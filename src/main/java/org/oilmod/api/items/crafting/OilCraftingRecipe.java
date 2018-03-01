package org.oilmod.api.items.crafting;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class OilCraftingRecipe {
    private final NMSCraftingRecipe nmsRecipe;

    public OilCraftingRecipe(NMSCraftingRecipe nmsRecipe) {
        this.nmsRecipe = nmsRecipe;
    }

    public NMSCraftingRecipe getNmsRecipe() {
        return nmsRecipe;
    }
}
