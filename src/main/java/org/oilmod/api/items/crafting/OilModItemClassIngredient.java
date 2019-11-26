package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.internal.ItemClassMap;
import org.oilmod.api.rep.itemstack.ItemStackRep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class OilModItemClassIngredient extends OilIngredientBase {
    private final Class<? extends OilItem> itemClass;
    private final boolean subTypes;
    private ItemStackRep[] examples;
    private int lastItemsCount;

    public OilModItemClassIngredient(Class<? extends OilItem> itemClass, boolean subTypes) {
        this.itemClass = itemClass;
        this.subTypes = subTypes;
    }

    @Override
    public boolean match(ItemStackRep itemStack, DataHolder dataHolder) {
        if (itemStack instanceof OilBukkitItemStack) {
            Class<? extends OilItem> itemClass2 =((OilBukkitItemStack) itemStack).getOilItemStack().getItem().getClass();
            return subTypes ? itemClass.isAssignableFrom(itemClass2) : itemClass.equals(itemClass2);
        }
        return false;
    }

    @Override
    public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
        OilItem[] items = ItemClassMap.getInstance().getOilItemsByClass(itemClass, subTypes);
        if (items.length != lastItemsCount || examples==null) {
            List<ItemStackRep> itemStacks = new ArrayList<>(items.length);
            for (OilItem item:items) {
                itemStacks.addAll(Arrays.asList(item.getNaturalExamples()));
            }
            examples = itemStacks.toArray(new ItemStackRep[0]);
        }
        return examples[rnd.nextInt(examples.length)];
    }
}
