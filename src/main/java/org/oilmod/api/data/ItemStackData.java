package org.oilmod.api.data;

import org.oilmod.api.inventory.InventoryFactory;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.userinterface.ItemStackHolder;

/**
 * Created by sirati97 on 26.06.2016 for OilMod-Api.
 */
public abstract class ItemStackData extends IDataBase<ItemStackRep> implements ItemStackHolder {
    public ItemStackData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public static ItemStackData createInstance(String name, IDataParent dataParent) {
        return InventoryFactory.getInstance().createItemStackData(name, dataParent);
    }

    @Override
    public final void setItemStack(ItemStackRep itemStack) {
        setData(itemStack);
    }

    @Override
    public final ItemStackRep getItemStack() {
        return getData();
    }
}
