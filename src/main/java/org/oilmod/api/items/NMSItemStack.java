package org.oilmod.api.items;

import org.oilmod.api.config.Compound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Internal
 */
public interface NMSItemStack {
    ItemStack asBukkitItemStack();
    OilItemStack getOilItemStack();
    int getDataNMS();
    void setDataNMS(int data);
    String getRenameNMS();
    void setRenameNMS(String name);

    boolean isRenamedNMS();
}
