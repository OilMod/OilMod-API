package org.oilmod.api.inventory;

import org.oilmod.api.data.CompoundSerializableData;
import org.oilmod.api.data.IDataParent;
import org.oilmod.api.data.ObjectFactory;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class InventoryData<T extends ModInventoryObjectBase<T>> extends CompoundSerializableData<ModNMSIInventory<T>> {
    private T oilApiInv;

    public InventoryData(String name, IDataParent dataParent, ObjectFactory<ModNMSIInventory<T>> factory, boolean recreateOnLoad) {
        super(name, dataParent, factory, recreateOnLoad);
    }

    public void setOilApiInv(T oilApiInv) {
        this.oilApiInv = oilApiInv;
        getData().setOilApiMirror(oilApiInv);
    }

    @Override
    protected void onCreated() {
        getData().setOilApiMirror(oilApiInv);
    }
}
