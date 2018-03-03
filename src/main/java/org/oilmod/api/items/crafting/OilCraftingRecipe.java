package org.oilmod.api.items.crafting;

import org.oilmod.api.util.OilKey;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class OilCraftingRecipe {
    private final NMSCraftingRecipe nmsRecipe;
    private final OilKey key;

    public OilCraftingRecipe(OilKey key, NMSCraftingRecipe nmsRecipe) {
        this.key = key;
        this.nmsRecipe = nmsRecipe;
    }

    public NMSCraftingRecipe getNmsRecipe() {
        return nmsRecipe;
    }

    public OilKey getOilKey() {
        return key;
    }
}
