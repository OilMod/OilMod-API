package de.sirati97.oilmod.api.items.crafting;


import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public interface OilCraftingResult {
    ItemStack preCraftResult(ItemStack[] matrix, boolean shaped, int width, int height);
    void craftResult(ItemStack result, ItemStack[] matrix, boolean shaped, int width, int height);
}
