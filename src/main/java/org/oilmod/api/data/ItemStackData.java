package org.oilmod.api.data;

import org.oilmod.api.inventory.InventoryFactory;
import org.oilmod.api.userinterface.ItemStackHolder;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-Api.
 */
public abstract class ItemStackData extends IDataBase<ItemStack> implements ItemStackHolder {
    public ItemStackData(String name, DataParent dataParent) {
        super(name, dataParent);
    }

    public static ItemStackData createInstance(String name, DataParent dataParent) {
        return InventoryFactory.getInstance().createItemStackData(name, dataParent);
    }

    @Override
    public final void setItemStack(ItemStack itemStack) {
        setData(itemStack);
    }

    @Override
    public final ItemStack getItemStack() {
        return getData();
    }
}
