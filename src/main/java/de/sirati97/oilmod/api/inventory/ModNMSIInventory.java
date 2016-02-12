package de.sirati97.oilmod.api.inventory;

import de.sirati97.oilmod.api.config.CompoundSerializable;
import org.bukkit.inventory.Inventory;

/**
 * Created by sirati97 on 12.02.2016.
 */
public interface ModNMSIInventory extends CompoundSerializable, Cloneable {
    Inventory getBukkitInventory();
    Object getNMSInventory();
    Object getOilModInventory();

}
