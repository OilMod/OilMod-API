package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.config.Compound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by sirati97 on 16.01.2016.
 */
public interface NMSItemStack {
    String getDisplayName();
    void setDisplayName(String name, boolean renamed);
    Compound getTagCompound();
    ItemStack asBukkitItemStack();
    void updateItemDescription(int oldSize, List<String> description);
    boolean hasLore();
}
