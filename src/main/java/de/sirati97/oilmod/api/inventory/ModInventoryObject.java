package de.sirati97.oilmod.api.inventory;

import de.sirati97.oilmod.api.data.CompoundSerializableData;
import org.bukkit.inventory.Inventory;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class ModInventoryObject {
    private final CompoundSerializableData<ModNMSIInventory> serializableDataHandler;

    public ModInventoryObject (CompoundSerializableData<ModNMSIInventory> serializableDataHandler) {
        this.serializableDataHandler = serializableDataHandler;
    }

    public CompoundSerializableData<ModNMSIInventory> getSerializableDataHandler() {
        return serializableDataHandler;
    }

    public Inventory getBukkitInventory() {
        return getSerializableDataHandler().getData().getBukkitInventory();
    }

    public Object getNMSInventory() {
        return getSerializableDataHandler().getData().getNMSInventory();
    }

    public String getNBTName() {
        return getSerializableDataHandler().getName();
    }


}
