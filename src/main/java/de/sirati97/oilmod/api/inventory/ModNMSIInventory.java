package de.sirati97.oilmod.api.inventory;

import de.sirati97.oilmod.api.config.CompoundSerializable;
import de.sirati97.oilmod.api.util.Tickable;
import org.bukkit.inventory.Inventory;

/**
 * Created by sirati97 on 12.02.2016.
 */
public interface ModNMSIInventory<T extends ModInventoryObjectBase> extends CompoundSerializable, Cloneable, Tickable {
    Inventory getBukkitInventory();
    Object getNMSInventory();
    Object getOilModInventory();
    void setOilApiMirror(T arg);
}
