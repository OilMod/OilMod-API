package org.oilmod.api.unification.material;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.OilMod;
import org.oilmod.api.registry.IKeySettable;
import org.oilmod.api.util.OilKey;

import java.util.*;
import java.util.function.Consumer;

import static org.oilmod.api.unification.material.MaterialHelper.unwrap;
import static org.oilmod.api.util.Util.checkName;
import static org.oilmod.util.LamdbaCastUtils.cast;

public class MaterialRequestBuilder<Type extends UniMaterial> implements IKeySettable {
    private OilMod requester;
    private Set<String> specialisations = new ObjectOpenHashSet<>();
    private Set<String> generalisations = new ObjectOpenHashSet<>();
    private Set<String> identifiers = new HashSet<>();
    private List<String> constituents = new ArrayList<>();
    private MaterialType type = MaterialType.Element;
    private List<Consumer<? extends UniMaterial>> futures = new ObjectArrayList<>();


    public MaterialRequestBuilder() {
    }

    public OilMod getRequester() {
        return requester;
    }
    
    public MaterialRequestBuilder<Type> future(Consumer<Type> future) {
        futures.add(future);
        return this;
    }

    public MaterialRequestBuilder<Type> identifiers(String... mats) {
        checkName(mats);
        Collections.addAll(identifiers, mats);
        return this;
    }

    public MaterialRequestBuilder<Type> specialisation(String... mats) {
        checkName(mats);
        Collections.addAll(specialisations, mats);
        return this;
    }

    public MaterialRequestBuilder<Type> generalisation(String... mats) {
        checkName(mats);
        Collections.addAll(generalisations, mats);
        return this;
    }
    
    public MaterialRequestBuilder<Composite> constituents(String... mats) {
        checkName(mats);
        type = MaterialType.Composite;
        Collections.addAll(constituents, mats);
        return cast(this);
    }


    public MaterialRequestBuilder<Category> category() {
        type = MaterialType.Category;
        return cast(this);
    }

    public MaterialRequestBuilder<Element> element() {
        type = MaterialType.Element;
        return cast(this);
    }

    public List<String> getConstituents() {
        return constituents;
    }

    public Set<String> getGeneralisations() {
        return generalisations;
    }

    public Set<String> getSpecialisations() {
        return specialisations;
    }

    public Set<String> getIdentifiers() {
        return identifiers;
    }

    public List<Consumer<? extends UniMaterial>> getFutures() {
        return futures;
    }

    @Override
    public void setOilKey(OilKey key) {
        identifiers(key.getKeyString());
        requester = key.getMod();
    }

    @Override
    public OilKey getOilKey() {
        throw new NotImplementedException("this operation is not supported!");
    }

    public MaterialType getType() {
        return type;
    }
}
