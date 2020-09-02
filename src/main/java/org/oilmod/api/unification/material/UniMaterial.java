package org.oilmod.api.unification.material;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.oilmod.api.util.Util.*;

public abstract class UniMaterial implements IUniMaterial {
    protected UniMaterial(OilMod requester, String... identifiers){
        requesters.add(requester);
        Collections.addAll(this.identifiers, identifiers);
        wrapper = MaterialHelper.HelperImpl.getInstance().createWrapper(this);
    }

    private final Set<OilMod> requesters = new ObjectOpenHashSet<>();
    private final Set<OilMod> requestersReadOnly = Collections.unmodifiableSet(requesters);
    private final Set<UniMaterial> specialisations = new ObjectOpenHashSet<>();
    private final Set<UniMaterial> generalisations = new ObjectOpenHashSet<>();
    private final Set<UniMaterial> variantSuppliers = new ObjectOpenHashSet<>();
    private final Set<String> identifiers = new HashSet<>();
    private final Set<String> identifiersReadOnly = Collections.unmodifiableSet(identifiers);  //todo this should be convertable into arrays once we freeze registration
    private UniMaterialWrapper wrapper;

    @Override
    public boolean isGeneralisation(UniMaterial mat, boolean directOnly) {
        if (directOnly)return generalisations.contains(mat);
        return containsRecursive(mat, generalisations, mat2 -> mat2.generalisations);
    }

    void addGeneralisation(UniMaterial mat) {
        Validate.isTrue(!isSpecialisation(mat, false), "cannot create circle reference");
        Validate.isTrue(!hasCommon(getSpecialisations(), mat.getGeneralisations()), "cannot create ring dependencies");
        //todo: reconsider which links might become removable, see common(G, mat.G)
        addGeneralisationInt(mat);
        mat.addSpecialisationInt(this);
    }

    private void addGeneralisationInt(UniMaterial mat) {
        if (isGeneralisation(mat, false))return; //someone already did better work
        generalisations.add(mat);
    }

    @Override
    public Iterable<UniMaterial> getGeneralisations(boolean directOnly) {
        return directOnly?
                () -> specialisations.iterator():
                () -> resolveRecursive(specialisations.stream(), mat2 -> mat2.specialisations.stream()).iterator();
    }



    //todo add a insert method

    public boolean isSpecialisation(UniMaterial mat, boolean directOnly) {
        if (directOnly)return specialisations.contains(mat);
        return containsRecursive(mat, specialisations, mat2 -> mat2.specialisations);
    }

    void addSpecialisation(UniMaterial mat) {
        Validate.isTrue(!isGeneralisation(mat, false), "cannot create circle reference");
        Validate.isTrue(!hasCommon(getGeneralisations(), mat.getSpecialisations()), "cannot create ring dependencies");
        //todo: reconsider which links might become removable, see common(S, mat.S)
        addSpecialisationInt(mat);
        mat.addGeneralisationInt(this);
    }

    private void addSpecialisationInt(UniMaterial mat) {
        if (isSpecialisation(mat, false))return; //someone already did better work
        specialisations.add(mat);
    }

    @Override
    public Iterable<UniMaterial> getSpecialisations(boolean directOnly) {
        return directOnly?
                () -> specialisations.iterator():
                () -> resolveRecursive(specialisations.stream(), mat2 -> mat2.specialisations.stream()).iterator();
    }



    //todo add a variant method

    public boolean isVariantSupplier(UniMaterial mat, boolean directOnly) {
        if (directOnly)return variantSuppliers.contains(mat);
        return containsRecursive(mat, variantSuppliers, mat2 -> mat2.variantSuppliers);
    }

    void addVariantSupplier(UniMaterial mat) {
        Validate.isTrue(!hasCommon(getVariantSuppliers(), mat.getVariantSuppliers()), "cannot create ring variant dependencies");
        addVariantSupplierInt(mat);
    }

    private void addVariantSupplierInt(UniMaterial mat) {
        if (isVariantSupplier(mat, false))return; //someone already did better work
        variantSuppliers.add(mat);
    }

    @Override
    public Iterable<UniMaterial> getVariantSuppliers(boolean directOnly) {
        return directOnly?
                () -> variantSuppliers.iterator():
                () -> resolveRecursive(variantSuppliers.stream(), mat2 -> mat2.variantSuppliers.stream()).iterator();
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

    void addRequester(OilMod mod) {
        requesters.add(mod);
    }

    void addIdentifiers(String... identifiers) {
        Collections.addAll(this.identifiers, identifiers);
    }
}
