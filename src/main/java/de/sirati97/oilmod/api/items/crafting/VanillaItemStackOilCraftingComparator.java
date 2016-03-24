package de.sirati97.oilmod.api.items.crafting;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaItemStackOilCraftingComparator extends VanillaOilCraftingComparator {
    private final ItemStack itemStack;

    public VanillaItemStackOilCraftingComparator(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public boolean match(ItemStack itemStack2, ComparatorData comparatorData) {
        return super.match(itemStack, comparatorData) && itemStack.isSimilar(itemStack2);
    }
}
