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

    private Set<OilMod> requesters = new ObjectOpenHashSet<>();
    private Set<OilMod> requestersReadOnly = Collections.unmodifiableSet(requesters);
    private Set<UniMaterial> specialisations = new ObjectOpenHashSet<>();
    private Set<UniMaterial> generalisations = new ObjectOpenHashSet<>();
    private Set<String> identifiers = new HashSet<>();
    private Set<String> identifiersReadOnly = Collections.unmodifiableSet(identifiers);
    private UniMaterialWrapper wrapper;

    public boolean isSpecialisation(UniMaterial mat, boolean directOnly) {
        if (directOnly)return specialisations.contains(mat);
        return containsRecursive(mat, specialisations, mat2 -> mat2.specialisations);
    }

    @Override
    public boolean isGeneralisation(UniMaterial mat, boolean directOnly) {
        if (directOnly)return generalisations.contains(mat);
        return containsRecursive(mat, generalisations, mat2 -> mat2.generalisations);
    }

    //todo add a insert method

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
    public Iterable<UniMaterial> getSpecialisations(boolean directOnly) {
        return directOnly?
                () -> specialisations.iterator():
                () -> resolveRecursive(specialisations.stream(), mat2 -> mat2.specialisations.stream()).iterator();
    }

    @Override
    public Iterable<UniMaterial> getGeneralisations(boolean directOnly) {
        return directOnly?
                () -> specialisations.iterator():
                () -> resolveRecursive(specialisations.stream(), mat2 -> mat2.specialisations.stream()).iterator();
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
