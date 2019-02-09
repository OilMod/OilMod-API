package org.oilmod.api.items.internal;

import org.oilmod.api.items.crafting.OilCraftingIngredient;
import org.oilmod.api.items.crafting.OilCraftingRecipe;
import org.oilmod.api.items.crafting.OilCraftingResult;
import org.oilmod.api.util.OilKey;

/**
 * Internal - should not be called by user code - Use ItemCraftingFactory instead
 */
public abstract class ItemCraftingFactory {
    private static ItemCraftingFactory instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemCraftingFactory instance) {
        if (ItemCraftingFactory.instance == null) {
            synchronized (MUTEX) {
                if (ItemCraftingFactory.instance == null) {
                    ItemCraftingFactory.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemCraftingFactory getInstance() {
        return instance;
    }

    public abstract OilCraftingRecipe createShapedRecipe(OilKey key, int width, int height, OilCraftingResult result, OilCraftingIngredient... ingredients);
    public abstract OilCraftingRecipe createShapelessRecipe(OilKey key, OilCraftingResult result, OilCraftingIngredient... ingredients);
    public abstract void registerGlobal(OilCraftingRecipe recipe);
}
