package org.oilmod.api.inventory;

import org.bukkit.inventory.FurnaceInventory;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class ModFurnaceInventoryObject extends ModInventoryObjectBase<ModFurnaceInventoryObject> {

    public ModFurnaceInventoryObject(InventoryData<ModFurnaceInventoryObject> serializableDataHandler) {
        super(serializableDataHandler);
    }

    @Override
    public FurnaceInventory getBukkitInventory() {
        return (FurnaceInventory) super.getBukkitInventory();
    }
}
