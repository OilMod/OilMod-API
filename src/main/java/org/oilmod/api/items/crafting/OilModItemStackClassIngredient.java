package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.itemstack.ItemStackRep;

import java.util.Random;

/**
 * WIP - DO NOT USE
 */
public class OilModItemStackClassIngredient extends OilIngredientBase {
    private final Class<? extends OilItemStack> itemClass;
    private int lastItemsCount;

    /**
     *
     * @deprecated this was never finished. i do not know a way to create examples for this ingredient
     */
    @Deprecated
    public OilModItemStackClassIngredient(Class<? extends OilItemStack> itemClass) {
        this.itemClass = itemClass;
        throw new UnsupportedOperationException("This ingredient type is not supported. :/ maybe in the future");
    }

    @Override
    public boolean match(ItemStackRep itemStack, DataHolder dataHolder) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack().getClass().equals(itemClass);
    }

    @Override
    public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
//        OilItemStackRep[] items = ItemClassMap.getInstance().getOilItemsByClass(itemClass);
//        if (items.length != lastItemsCount || examples==null) {
//            List<ItemStack> itemStacks = new ArrayList<>(items.length);
//            for (OilItemStack item:items) {
//                itemStacks.addAll(Arrays.asList(item.getNaturalExamples()));
//            }
//            examples = itemStacks.toArray(new ItemStack[itemStacks.size()]);
//        }
//        return examples[rnd.nextInt(examples.length)];
        return null;
    }
}
