package de.sirati97.oilmod.api.items.crafting;


import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public interface OilCraftingComparator {
    boolean match(ItemStack itemStack, ComparatorData comparatorData);
}
