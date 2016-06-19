package de.sirati97.oilmod.api.items;

import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 06.03.2016.
 */
public interface OilSpecificItemstackFactory {
    /**
     * @return should return a new ItemStack
     */
    ItemStack create();
}
