package de.sirati97.oilmod.api.inventory;

import org.bukkit.inventory.CraftingInventory;

/**
 * Created by sirati97 on 26.02.2016.
 */
public class ModPortableCraftingInventoryObject extends ModInventoryObjectBase<ModPortableCraftingInventoryObject> {

    public ModPortableCraftingInventoryObject(InventoryData<ModPortableCraftingInventoryObject> serializableDataHandler) {
        super(serializableDataHandler);
    }

    @Override
    public CraftingInventory getBukkitInventory() {
        return (CraftingInventory) super.getBukkitInventory();
    }
}
