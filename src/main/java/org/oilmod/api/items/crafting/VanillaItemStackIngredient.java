package org.oilmod.api.items.crafting;


import org.oilmod.api.rep.itemstack.ItemStackRep;

import java.util.Random;

/**
 * Created by sirati97 on 24.03.2016.
 */
public class VanillaItemStackIngredient extends VanillaIngredientBase {
    private final ItemStackRep itemStack;

    public VanillaItemStackIngredient(ItemStackRep itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public boolean match(ItemStackRep itemStack2, DataHolder dataHolder) {
        return super.match(itemStack, dataHolder) && itemStack.isSimilar(itemStack2);
    }

    @Override
    public ItemStackRep getRandomExample(Random rnd, DataHolder dataHolder) {
        return itemStack;
    }
}
