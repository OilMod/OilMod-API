package org.oilmod.api.inventory;


import org.oilmod.api.rep.inventory.InventoryRep;

/**
 * Created by sirati97 on 26.02.2016.
 */
public class ModPortableCraftingInventoryObject extends ModInventoryObjectBase<ModPortableCraftingInventoryObject> {

    public ModPortableCraftingInventoryObject(InventoryData<ModPortableCraftingInventoryObject> serializableDataHandler) {
        super(serializableDataHandler);
    }

    @Override
    public InventoryRep getBukkitInventory() {
        return (InventoryRep) super.getBukkitInventory();
    } //TODO: add crafting table specific inventory
}
