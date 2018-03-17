package org.oilmod.api.items;

import org.oilmod.api.config.Compound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Internal
 */
public interface NMSItemStack {
    String getDisplayName();
    void setDisplayName(String name, boolean renamed);
    Compound getTagCompound();
    ItemStack asBukkitItemStack();
    OilItemStack getOilItemStack();
    void updateItemDescription(int oldSize, List<String> description);
    boolean hasLore();
}
