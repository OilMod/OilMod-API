package org.oilmod.api.unification.item;

import org.oilmod.api.unification.ExpressableBase;
import org.oilmod.api.unification.material.UniMaterial;

public class ItemExpressable extends ExpressableBase<UniItem, UniItemBuilder> {
    @Override
    public UniItemBuilder createStandardBuilder(UniMaterial mat) {
        return null;
    }
}
