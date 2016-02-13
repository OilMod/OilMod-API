package de.sirati97.oilmod.api.inventory;

import org.bukkit.inventory.Inventory;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class ModInventoryObjectBase<T extends ModInventoryObjectBase> {
    private final InventoryData<T> serializableDataHandler;

    public ModInventoryObjectBase(InventoryData<T> serializableDataHandler) {
        this.serializableDataHandler = serializableDataHandler;
        serializableDataHandler.setOilApiInv((T) this);

    }

    public InventoryData<T> getSerializableDataHandler() {
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
