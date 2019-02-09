package org.oilmod.api.inventory;


import org.oilmod.api.rep.inventory.InventoryRep;

/**
 * Created by sirati97 on 13.02.2016.
 */
public class ModFurnaceInventoryObject extends ModInventoryObjectBase<ModFurnaceInventoryObject> {

    public ModFurnaceInventoryObject(InventoryData<ModFurnaceInventoryObject> serializableDataHandler) {
        super(serializableDataHandler);
    }

    @Override
    public InventoryRep getBukkitInventory() {
        return (InventoryRep) super.getBukkitInventory();
    } //TODO: add furnace specific inventory
}
