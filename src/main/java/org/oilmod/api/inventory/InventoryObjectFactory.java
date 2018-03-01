package org.oilmod.api.inventory;

/**
 * Created by sirati97 on 12.02.2016.
 */
public interface InventoryObjectFactory<T extends ModInventoryObjectBase> {
    T create(InventoryData<T> iData);
}
