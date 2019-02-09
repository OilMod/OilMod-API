package org.oilmod.api.items.crafting;

import org.oilmod.api.items.OilBukkitItemStack;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.rep.itemstack.ItemStackRep;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class OilModItemIngredient extends OilIngredientBase {
    private final OilItem item;
    private ItemStackRep[] examples;
    private int lastItemsCount;

    public OilModItemIngredient(OilItem item) {
        this.item = item;
    }

    @Override
    public boolean match(ItemStackRep itemStack, DataHolder dataHolder) {
        return itemStack instanceof OilBukkitItemStack && ((OilBukkitItemStack) itemStack).getOilItemStack().getItem().equals(item);
    }

    @Override
    public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
        return item.getNaturalExamples()[rnd.nextInt(item.getNaturalExamples().length)];
    }
}
