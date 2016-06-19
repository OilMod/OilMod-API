package de.sirati97.oilmod.api.items.crafting;


import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public interface OilCraftingResult {
    /**
     * Called when a preview of the result is needed. Do not change anything from the ingredients
     * @param matrix 1-dimensional array of all the crafting ingredients
     * @param shaped whether the recipe is shaped.
     * @param width width of the crafting grid
     * @param height height of the crafting grid
     * @return
     */
    ItemStack preCraftResult(ItemStack[] matrix, boolean shaped, int width, int height);

    /**
     * Called when the user crafts the item. It should do anything that could not be done in preCraftResult.
     * @param result The itemstack the is going to be returned
     * @param matrix 1-dimensional array of all the crafting ingredients
     * @param shaped whether the recipe is shaped.
     * @param width width of the crafting grid
     * @param height height of the crafting grid
     */
    void craftResult(ItemStack result, ItemStack[] matrix, boolean shaped, int width, int height);
}
