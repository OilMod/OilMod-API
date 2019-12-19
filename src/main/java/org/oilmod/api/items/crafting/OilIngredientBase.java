package org.oilmod.api.items.crafting;


import org.oilmod.api.rep.itemstack.ItemStackFactory;
import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Created by sirati97 on 03.04.2016.
 */
public abstract class OilIngredientBase implements OilCraftingIngredient {

    @Override
    public ItemStackRep onCrafted(ItemStackRep oldItemStack, DataHolder dataHolder) {
        oldItemStack.setAmount(oldItemStack.getAmount()-1);
        return oldItemStack.getAmount()>0?oldItemStack: ItemStackFactory.INSTANCE.empty();
    }

    @Override
    public boolean matchesNull() {
        return false;
    }
}
