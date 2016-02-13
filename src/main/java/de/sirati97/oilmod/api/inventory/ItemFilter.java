package de.sirati97.oilmod.api.inventory;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 13.02.2016.
 */
public interface ItemFilter {
    boolean allowed(ItemStack itemStack);
}
