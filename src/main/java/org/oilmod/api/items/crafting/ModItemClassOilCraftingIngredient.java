package org.oilmod.api.items.crafting;

import org.oilmod.api.items.internal.ItemClassMapSingleton;
import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItemBase;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class ModItemClassOilCraftingIngredient extends NormalOilCraftingIngredientBase {
    private final Class<? extends OilItemBase> itemClass;
    private ItemStack[] examples;
    private int lastItemsCount;

    public ModItemClassOilCraftingIngredient(Class<? extends OilItemBase> itemClass) {
        this.itemClass = itemClass;
    }

    @Override
    public boolean match(ItemStack itemStack, DataHolder dataHolder) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack().getItem().getClass().equals(itemClass);
    }

    @Override
    public ItemStack getRandomExample(Random rnd, DataHolder dataHolder) {
        OilItemBase[] items = ItemClassMapSingleton.getInstance().getOilItemsByClass(itemClass);
        if (items.length != lastItemsCount || examples==null) {
            List<ItemStack> itemStacks = new ArrayList<>(items.length);
            for (OilItemBase item:items) {
                itemStacks.addAll(Arrays.asList(item.getNaturalExamples()));
            }
            examples = itemStacks.toArray(new ItemStack[itemStacks.size()]);
        }
        return examples[rnd.nextInt(examples.length)];
    }
}
