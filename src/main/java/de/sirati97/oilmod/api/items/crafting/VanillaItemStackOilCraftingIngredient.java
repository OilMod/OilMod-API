package de.sirati97.oilmod.api.items.crafting;

import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaItemStackOilCraftingIngredient extends VanillaOilCraftingIngredient {
    private final ItemStack itemStack;

    public VanillaItemStackOilCraftingIngredient(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public boolean match(ItemStack itemStack2, ComparatorData comparatorData) {
        return super.match(itemStack, comparatorData) && itemStack.isSimilar(itemStack2);
    }

    @Override
    public ItemStack getRandomExample(Random rnd) {
        return itemStack;
    }
}
