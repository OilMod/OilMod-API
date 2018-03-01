package org.oilmod.api.items;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 06.03.2016.
 */
public interface OilSpecificItemStackFactory {
    /**
     * @return should return a new ItemStack
     */
    ItemStack create();
}
