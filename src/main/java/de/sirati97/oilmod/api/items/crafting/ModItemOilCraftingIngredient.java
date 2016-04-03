package de.sirati97.oilmod.api.items.crafting;

import de.sirati97.oilmod.api.items.OilBukkitItemStack;
import de.sirati97.oilmod.api.items.OilItemBase;
import de.sirati97.oilmod.api.items.internal.ItemClassMapSingleton;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class ModItemOilCraftingIngredient extends NormalOilCraftingIngredientBase {
    private Class<? extends OilItemBase> itemClass;
    private ItemStack[] examples;
    private int lastItemsCount;

    public ModItemOilCraftingIngredient(Class<? extends OilItemBase> itemClass) {
        this.itemClass = itemClass;
    }

    @Override
    public boolean match(ItemStack itemStack, ComparatorData comparatorData) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack().getItem().getClass().equals(itemClass);
    }

    @Override
    public ItemStack getRandomExample(Random rnd) {
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
