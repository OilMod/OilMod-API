package org.oilmod.api.items.crafting;

import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaItemStackIngredient extends VanillaIngredientBase {
    private final ItemStack itemStack;

    public VanillaItemStackIngredient(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public boolean match(ItemStack itemStack2, DataHolder dataHolder) {
        return super.match(itemStack, dataHolder) && itemStack.isSimilar(itemStack2);
    }

    @Override
    public ItemStack getRandomExample(Random rnd, DataHolder dataHolder) {
        return itemStack;
    }
}
