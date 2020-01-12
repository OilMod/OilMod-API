package org.oilmod.api.unification.material;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;

import java.util.*;

import static org.oilmod.api.unification.material.MaterialHelper.unwrap;

public class MaterialBuilder {
    private static final UniMaterial[] MATERIALS_EMPTY_ARRAY = new UniMaterial[0];
    private static final String[] String_EMPTY_ARRAY = new String[0];
    private final MaterialHelper.HelperImpl helper = MaterialHelper.HelperImpl.getInstance();
    private final OilMod requester;
    private Set<UniMaterial> specialisations = new ObjectOpenHashSet<>();
    private Set<UniMaterial> generalisations = new ObjectOpenHashSet<>();
    private Set<String> identifiers = new HashSet<>();
    private List<UniMaterial> constituents = new ArrayList<>();


    public MaterialBuilder(OilMod requester) {
        Validate.notNull(requester);
        this.requester = requester;
    }

    public OilMod getRequester() {
        return requester;
    }

    public MaterialBuilder identifiers(String... mats) {
        Collections.addAll(identifiers, mats);
        return this;
    }

    public MaterialBuilder specialisation(UniMaterial... mats) {
        Collections.addAll(specialisations, mats);
        return this;
    }

    public MaterialBuilder specialisation(IUniMaterial... mats) {
        specialisation(unwrap(mats));
        return this;
    }

    public MaterialBuilder generalisation(UniMaterial... mats) {
        Collections.addAll(generalisations, mats);
        return this;
    }

    public MaterialBuilder generalisation(IUniMaterial... mats) {
        generalisation(unwrap(mats));
        return this;
    }

    public MaterialBuilder constituents(UniMaterial... mats) {
        Collections.addAll(constituents, mats);
        return this;
    }

    public MaterialBuilder constituents(IUniMaterial... mats) {
        constituents(unwrap(mats));
        return this;
    }

    private <T extends UniMaterial> T init(T result) {
        result = helper.registerMaterial(result);
        helper.addGeneralisations(result, generalisations.toArray(MATERIALS_EMPTY_ARRAY));
        helper.addSpecialisations(result, specialisations.toArray(MATERIALS_EMPTY_ARRAY));
        return result;
    }

    public Category createCategory() {
        Validate.notEmpty(identifiers);
        return init(helper.createCategory(requester, identifiers.toArray(String_EMPTY_ARRAY)));
    }

    public Composite createComposite() {
        Validate.notEmpty(identifiers);
        Validate.notEmpty(constituents);
        return init(helper.createComposite(requester, constituents.toArray(MATERIALS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
    }

    public Element createElement() {
        Validate.notEmpty(identifiers);
        return init(helper.createElement(requester, identifiers.toArray(String_EMPTY_ARRAY)));
    }

    public OrganicComponent createOrganic() {
        Validate.notEmpty(identifiers);
        return init(helper.createOrganic(requester, identifiers.toArray(String_EMPTY_ARRAY)));
    }
}
