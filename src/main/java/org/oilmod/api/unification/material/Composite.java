package org.oilmod.api.unification.material;

import gnu.trove.set.hash.THashSet;
import org.oilmod.api.OilMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Composite extends UniMaterial {
    protected Composite(OilMod requester, UniMaterial[] constituents, String... identifiers) {
        super(requester, identifiers);
        Collections.addAll(this.constituents, constituents);
    }

    private List<UniMaterial> constituents = new ArrayList<>();
    private List<UniMaterial> constituentsReadOnly = Collections.unmodifiableList(constituents);

    public List<UniMaterial> getConstituents() {
        return constituentsReadOnly;
    }
}
