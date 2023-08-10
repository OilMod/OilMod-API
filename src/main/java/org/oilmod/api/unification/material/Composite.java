package org.oilmod.api.unification.material;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.oilmod.api.OilMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Composite extends UniMaterial {
    protected Composite(OilMod[] requesters, UniMaterial[] constituents, String... identifiers) {
        super(requesters, identifiers);
        Collections.addAll(this.constituents, constituents);
    }

    protected List<UniMaterial> constituents = new ArrayList<>();
    private List<UniMaterial> constituentsReadOnly = Collections.unmodifiableList(constituents);

    public List<UniMaterial> getConstituents() {
        return constituentsReadOnly;
    }

    @Override
    public String toString() {
        String result = super.toString();
        return result.substring(0, result.length()-1) +
                ", constituents=" + constituents.stream().map(UniMaterial::getMainIdentifier).collect(Collectors.joining(", ", "(", ")")) +
                '}';
    }


    @Override
    public MaterialType getType() {
        return MaterialType.Composite;
    }
}
