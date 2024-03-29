package org.oilmod.api.unification.material;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;
import org.oilmod.api.util.LazyValidate;

import javax.swing.plaf.synth.SynthTreeUI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.oilmod.api.util.Util.*;

public abstract class UniMaterial implements IUniMaterial {

    private static final String ERR_FROZEN = "Cannot at %s as %s is already frozen";
    private static final String ERR_RING = "Cannot create ring variant dependencies";
    private static final String ERR_CIRCLE = "Cannot create circle reference: %s";

    protected UniMaterial(OilMod[] requesters, String... identifiers){
        Collections.addAll(this.requesters, requesters);
        Collections.addAll(this.identifiers, identifiers);
        mainIdentifier = identifiers[0];
        wrapper = MaterialHelper.HelperImpl.getInstance().createWrapper(this);
    }

    private final Set<OilMod> requesters = new ObjectOpenHashSet<>();
    private final Set<OilMod> requestersReadOnly = Collections.unmodifiableSet(requesters);
    private final Set<UniMaterial> specialisations = new ObjectOpenHashSet<>();
    private final Set<UniMaterial> generalisations = new ObjectOpenHashSet<>();
    private final Set<UniMaterial> variantSuppliers = new ObjectOpenHashSet<>();
    private final Set<String> identifiers = new HashSet<>();
    private final String mainIdentifier;
    private final Set<String> identifiersReadOnly = Collections.unmodifiableSet(identifiers);  //todo this should be convertable into arrays once we freeze registration
    private UniMaterialWrapper wrapper;
    private boolean frozen = false;

    @Override
    public boolean isGeneralisation(UniMaterial mat, boolean directOnly) {
        if (directOnly)return generalisations.contains(mat);
        return containsRecursive(mat, generalisations, mat2 -> mat2.generalisations);
    }

    void addGeneralisation(UniMaterial mat) {
        LazyValidate.isTrue(!frozen, ERR_FROZEN, ()->mat, ()->this);
        LazyValidate.isTrue(!isSpecialisation(mat, false), ERR_CIRCLE, ()->findLoop(mat, this));

        //todo: reconsider which links might become removable, see common(G, mat.G)
        addGeneralisationInt(mat);
        mat.addSpecialisationInt(this);
    }

    private void addGeneralisationInt(UniMaterial mat) {
        LazyValidate.isTrue(!frozen, ERR_FROZEN, ()->mat, ()->this);
        if (isGeneralisation(mat, false))return; //someone already did better work
        generalisations.add(mat);
    }

    @Override
    public Stream<UniMaterial> getGeneralisations(boolean directOnly, boolean includeSelf) {
        Stream<UniMaterial> result = directOnly?
                generalisations.stream():
                resolveRecursive(generalisations.stream(), mat2 -> mat2.generalisations.stream());
        if (includeSelf)result = Stream.concat(Stream.of(this), result);
        return result;
    }



    //todo add a insert method

    public boolean isSpecialisation(UniMaterial mat, boolean directOnly) {
        if (directOnly)return specialisations.contains(mat);
        return containsRecursive(mat, specialisations, mat2 -> mat2.specialisations);
    }

    void addSpecialisation(UniMaterial mat) {
        LazyValidate.isTrue(!frozen, ERR_FROZEN, ()->mat, ()->this);
        LazyValidate.isTrue(!isGeneralisation(mat, false), ERR_CIRCLE, ()->findLoop(this, mat));

        //todo: reconsider which links might become removable, see common(S, mat.S)
        addSpecialisationInt(mat);
        mat.addGeneralisationInt(this);
    }

    private void addSpecialisationInt(UniMaterial mat) {
        LazyValidate.isTrue(!frozen, ERR_FROZEN, ()->mat, ()->this);
        if (isSpecialisation(mat, false))return; //someone already did better work
        specialisations.add(mat);
    }

    @Override
    public Stream<UniMaterial> getSpecialisations(boolean directOnly, boolean includeSelf) {
        Stream<UniMaterial> result = directOnly?
                specialisations.stream():
                resolveRecursive(specialisations.stream(), mat2 -> mat2.specialisations.stream());
        if (includeSelf)result = Stream.concat(Stream.of(this), result);
        return result;
    }



    //todo add a variant method

    public boolean isVariantSupplier(UniMaterial mat, boolean directOnly) {
        if (directOnly)return variantSuppliers.contains(mat);
        return containsRecursive(mat, variantSuppliers, mat2 -> mat2.variantSuppliers);
    }

    void addVariantSupplier(UniMaterial mat) {
        LazyValidate.isTrue(!frozen, ERR_FROZEN, ()->mat, ()->this);
        Validate.isTrue(!hasCommon(getVariantSuppliers(), mat.getVariantSuppliers()), ERR_RING);
        addVariantSupplierInt(mat);
    }

    private void addVariantSupplierInt(UniMaterial mat) {
        LazyValidate.isTrue(!frozen, ERR_FROZEN, ()->mat, ()->this);
        if (isVariantSupplier(mat, false))return; //someone already did better work
        variantSuppliers.add(mat);
    }

    @Override
    public Stream<UniMaterial> getVariantSuppliers(boolean directOnly) {
        return directOnly?
                variantSuppliers.stream() :
                resolveRecursive(variantSuppliers.stream(), mat2 -> mat2.variantSuppliers.stream());
    }


    @Override
    public Stream<UniMaterial> getVariants(boolean directOnly) {
        return getVariantSuppliers(directOnly).flatMap(mat2 -> mat2.getSpecialisations(directOnly, true)).filter(mat -> mat.getType() != MaterialType.Category).distinct();
    }

    @Override
    public Set<OilMod> getRequesters() {
        return requestersReadOnly;
    }

    @Override
    public Set<String> getIdentifiers() {
        return identifiersReadOnly;
    }

    @Override
    public UniMaterialWrapper getWrapper() {
        return wrapper;
    }

    public String getMainIdentifier() {
        return mainIdentifier;
    }

    void addRequester(OilMod mod) {
        requesters.add(mod);
    }

    void addIdentifiers(String... identifiers) {
        LazyValidate.isTrue(!frozen, ERR_FROZEN, ()->identifiers, ()->this);
        Collections.addAll(this.identifiers, identifiers);
    }

    public void freeze() {
        if (!frozen) {
            frozen = true;
            specialisations.forEach(UniMaterial::freeze);
        }
    }

    private static CharSequence findLoop(UniMaterial specialized, UniMaterial generalised) {
        UniMaterial last = specialized;
        StringBuilder sb = new StringBuilder();
        boolean success = false;
        while (generalised.isSpecialisation(last, false)) {
            sb.append(last.getMainIdentifier());
            sb.append(" -> ");
            boolean found = false;
            for (UniMaterial toTest:over(last.getGeneralisations(true, false))) {
                if (generalised.isSpecialisation(toTest, false) || generalised == toTest) {
                    last = toTest;
                    found = true;
                    break;
                }
            }
            if (!found || generalised == last) {
                break;
            } else {
                success = true;
            }
        }
        if (!success) { //try other direction
            last = generalised;
            sb = new StringBuilder();
            while (specialized.isGeneralisation(last, false)) {
                sb.append(last.getMainIdentifier());
                sb.append(" -> ");
                boolean found = false;
                for (UniMaterial toTest:over(last.getSpecialisations(true, false))) {
                    if (specialized.isGeneralisation(toTest, false) || specialized == toTest) {
                        last = toTest;
                        found = true;
                        break;
                    }
                }
                if (!found || specialized == last) {
                    break;
                }
            }
        }

        if ((success && last == generalised) || (!success && last == specialized)) {
            sb.append((success?generalised:specialized).getMainIdentifier());
            sb.append(" -> ");
            sb.append((success? specialized : generalised).getMainIdentifier());
        } else {
            sb.append("ERROR, could not find loop, failed at: ");
            sb.append(last.getMainIdentifier());
            sb.append("=/=");
            sb.append((success?generalised:specialized).getMainIdentifier());
        }


        return sb;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "identifiers=" + identifiers.stream().collect(Collectors.joining(", ", "(", ")")) +
                ", requesters=" + requesters.stream().map(OilMod::getInternalName).collect(Collectors.joining(", ", "(", ")")) +
                ", specialisations=" + specialisations.stream().map(UniMaterial::getMainIdentifier).collect(Collectors.joining(", ", "(", ")")) +
                ", generalisations=" + generalisations.stream().map(UniMaterial::getMainIdentifier).collect(Collectors.joining(", ", "(", ")")) +
                ", variantSuppliers=" + variantSuppliers.stream().map(UniMaterial::getMainIdentifier).collect(Collectors.joining(", ", "(", ")")) +
                '}';
    }
}
