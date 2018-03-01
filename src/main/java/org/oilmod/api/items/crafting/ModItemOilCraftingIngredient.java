package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItemBase;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class ModItemOilCraftingIngredient extends NormalOilCraftingIngredientBase {
    private final OilItemBase item;
    private ItemStack[] examples;
    private int lastItemsCount;

    public ModItemOilCraftingIngredient(OilItemBase item) {
        this.item = item;
    }

    @Override
    public boolean match(ItemStack itemStack, DataHolder dataHolder) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack().getItem().equals(item);
    }

    @Override
    public ItemStack getRandomExample(Random rnd, DataHolder dataHolder) {
        return item.getNaturalExamples()[rnd.nextInt(item.getNaturalExamples().length)];
    }
}
