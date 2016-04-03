package de.sirati97.oilmod.api.items.crafting;


import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public interface OilCraftingIngredient {
    boolean match(ItemStack itemStack, ComparatorData comparatorData);
    ItemStack getRandomExample(Random rnd);
    ItemStack onCrafted(ItemStack oldItemStack);
}
