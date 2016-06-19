package de.sirati97.oilmod.api.items.internal;

import de.sirati97.oilmod.api.items.crafting.OilCraftingRecipe;
import de.sirati97.oilmod.api.items.crafting.OilCraftingResult;
import de.sirati97.oilmod.api.items.crafting.OilCraftingIngredient;

/**
 * Created by sirati97 on 15.01.2016.
 */
public abstract class ItemCraftingFactoryBase {
    private static ItemCraftingFactoryBase instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemCraftingFactoryBase instance) {
        if (ItemCraftingFactoryBase.instance == null) {
            synchronized (MUTEX) {
                if (ItemCraftingFactoryBase.instance == null) {
                    ItemCraftingFactoryBase.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemCraftingFactoryBase getInstance() {
        return instance;
    }

    public abstract OilCraftingRecipe createShapedRecipe(int width, int height, OilCraftingResult result, OilCraftingIngredient... ingredients);
    public abstract OilCraftingRecipe createShapelessRecipe(OilCraftingResult result, OilCraftingIngredient... ingredients);
    public abstract void registerGlobal(OilCraftingRecipe recipe);
}