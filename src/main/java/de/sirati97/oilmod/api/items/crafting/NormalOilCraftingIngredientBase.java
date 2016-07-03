package de.sirati97.oilmod.api.items.crafting;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 03.04.2016.
 */
public abstract class NormalOilCraftingIngredientBase implements OilCraftingIngredient {

    @Override
    public ItemStack onCrafted(ItemStack oldItemStack, DataHolder dataHolder) {
        oldItemStack.setAmount(oldItemStack.getAmount()-1);
        return oldItemStack.getAmount()>0?oldItemStack:null;
    }

    @Override
    public boolean matchesNull() {
        return false;
    }
}
