package org.oilmod.api.unification.material;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;

import java.lang.Character.UnicodeBlock;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.oilmod.api.unification.material.MaterialHelper.unwrap;

public class MaterialBuilder {
    private static final UniMaterial[] MATERIALS_EMPTY_ARRAY = new UniMaterial[0];
    private static final OilMod[] MODS_EMPTY_ARRAY = new OilMod[0];
    private static final String[] String_EMPTY_ARRAY = new String[0];
    private final MaterialHelper.HelperImpl helper = MaterialHelper.HelperImpl.getInstance();
    protected final Set<OilMod> requesters = new ObjectOpenHashSet<>();
    protected final  Set<String> identifiers = new HashSet<>();
    private final Set<UniMaterial> specialisations = new ObjectOpenHashSet<>();
    private final  Set<UniMaterial> generalisations = new ObjectOpenHashSet<>();
    private final  List<UniMaterial> constituents = new ArrayList<>();
    private final Set<MaterialBuilder> specialisationBuilders = new ObjectOpenHashSet<>();
    private final  Set<MaterialBuilder> generalisationBuilders = new ObjectOpenHashSet<>();
    private final  List<MaterialBuilder> constituentBuilders = new ArrayList<>();
    private UniMaterial build = null;
    private final Set<BiConsumer<MaterialBuilder, UniMaterial>> futures = new ObjectOpenHashSet<>();
    private final Set<Consumer<UniMaterial>> lazyFutures = new ObjectOpenHashSet<>();

    private MaterialType type = MaterialType.Element;

    public void mergeIn(MaterialBuilder other) {
        constituentBuilders.addAll(other.constituentBuilders);
        specialisationBuilders.addAll(other.specialisationBuilders);
        generalisationBuilders.addAll(other.generalisationBuilders);
        constituents.addAll(other.constituents);
        specialisations.addAll(other.specialisations);
        generalisations.addAll(other.generalisations);
        identifiers.addAll(other.identifiers);
        requesters.addAll(other.requesters);
        futures.addAll(other.futures);
        lazyFutures.addAll(other.lazyFutures);
    }


    public MaterialBuilder(OilMod requester) {
        Validate.notNull(requester);
        this.requesters.add(requester);
    }


    public MaterialBuilder requester(OilMod... requesters) {
        if (build != null) {
            Arrays.stream(requesters).forEach((r)->build.addRequester(r));
        }
        Collections.addAll(this.requesters, requesters);
        return this;
    }

    public MaterialBuilder identifier(String... mats) {
        if (build != null) {
            build.addIdentifiers(mats);
        }
        Collections.addAll(this.identifiers, mats);
        return this;
    }

    public MaterialBuilder specialisation(MaterialBuilder... mats) {
        if (build == null) {
            //Collections.addAll(this.specialisationBuilders, mats);
        }
        Arrays.stream(mats).forEach((r)-> r.futures.add((builder, mat) -> {
                        MaterialBuilder.this.specialisation(mat);
                        specialisationBuilders.remove(builder);
                    }));
        return this;
    }


    public MaterialBuilder specialisation(UniMaterial... mats) {
        if (build != null) {
            Arrays.stream(mats).forEach((r)->build.addSpecialisation(r));
        }
        Collections.addAll(this.specialisations, mats);
        return this;
    }

    public MaterialBuilder specialisation(IUniMaterial... mats) {
        specialisation(unwrap(mats));
        return this;
    }

    public MaterialBuilder generalisation(MaterialBuilder... mats) {
        if (build == null) {
            Collections.addAll(this.generalisationBuilders, mats);
        }
        Arrays.stream(mats).forEach((r)-> r.futures.add((builder, mat) -> {
            MaterialBuilder.this.generalisation(mat);
            generalisationBuilders.remove(builder);
        }));
        return this;
    }

    public MaterialBuilder generalisation(UniMaterial... mats) {
        if (build != null) {
            Arrays.stream(mats).forEach((r)->build.addGeneralisation(r));
        }
        Collections.addAll(generalisations, mats);
        return this;
    }

    public MaterialBuilder generalisation(IUniMaterial... mats) {
        generalisation(unwrap(mats));
        return this;
    }

    public MaterialBuilder constituent(MaterialBuilder... mats) {
        if (build == null) {
            Collections.addAll(this.constituentBuilders, mats);
        }
        Arrays.stream(mats).forEach((r)-> r.futures.add((builder, mat) -> {
            MaterialBuilder.this.constituent(mat);
            constituentBuilders.remove(builder);
        }));
        return this;
    }

    public MaterialBuilder constituent(UniMaterial... mats) {
        if (build != null) {
            Collections.addAll(((Composite)build).constituents, mats);
        }
        Collections.addAll(this.constituents, mats);
        return this;
    }

    public MaterialBuilder constituent(IUniMaterial... mats) {
        constituent(unwrap(mats));
        return this;
    }

    private <T extends UniMaterial> T init(T result) {
        result = helper.registerMaterial(result);
        helper.addGeneralisations(result, generalisations.toArray(MATERIALS_EMPTY_ARRAY));
        helper.addSpecialisations(result, specialisations.toArray(MATERIALS_EMPTY_ARRAY));
        build = result;
        futures.forEach(c -> c.accept(this, build));
        return result;
    }

    protected UniMaterial getBuild() {
        return build;
    }

    public UniMaterial build() {
        if (build != null)return build;
        switch (type) {

            case Category:
                return init(helper.createCategory(requesters.toArray(MODS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
            case Element:
                return init(helper.createElement(requesters.toArray(MODS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
            case Composite:
                return init(helper.createComposite(requesters.toArray(MODS_EMPTY_ARRAY), constituents.toArray(MATERIALS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
        }
        throw new IllegalStateException(String.format("how did we get here? switch not exhaustive? got %s", type));
    }

    public MaterialBuilder setType(MaterialType type) {
        this.type = type;
        return this;
    }

    public MaterialType getType() {
        return type;
    }

    public MaterialBuilder lazyFuture(Consumer<UniMaterial> lazy) {
        lazyFutures.add(lazy);
        return this;
    }

    public void callLazyFutures() {
        lazyFutures.forEach(f -> f.accept(build));
    }

    /*public Category createCategory() {
        Validate.notEmpty(identifiers);
        return init(helper.createCategory(requesters.toArray(MODS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
    }

    public Composite createComposite() {
        Validate.notEmpty(identifiers);
        Validate.notEmpty(constituents);
        return init(helper.createComposite(requesters.toArray(MODS_EMPTY_ARRAY), constituents.toArray(MATERIALS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
    }

    public Element createElement() {
        Validate.notEmpty(identifiers);
        return init(helper.createElement(requesters.toArray(MODS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
    }

    public OrganicComponent createOrganic() {
        Validate.notEmpty(identifiers);
        return init(helper.createOrganic(requesters.toArray(MODS_EMPTY_ARRAY), identifiers.toArray(String_EMPTY_ARRAY)));
    }*/
}
