package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.config.Compound;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 16.01.2016.
 */
public interface NMSItemStack {
    String getDisplayName();
    void setDisplayName(String name);
    Compound getTagCompound();
    ItemStack asBukkitItemStack();
}
