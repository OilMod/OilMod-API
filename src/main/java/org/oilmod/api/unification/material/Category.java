package org.oilmod.api.unification.material;

import org.oilmod.api.OilMod;

public class Category extends UniMaterial {
    protected Category(OilMod[] requesters, String... identifiers) {
        super(requesters, identifiers);
    }


    @Override
    public MaterialType getType() {
        return MaterialType.Category;
    }
}
