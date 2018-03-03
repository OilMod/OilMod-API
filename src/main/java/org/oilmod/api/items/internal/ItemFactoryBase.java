package org.oilmod.api.items.internal;

import org.oilmod.api.items.OilItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Internal - should not be called by user code
 */
public abstract class ItemFactoryBase {
    private static ItemFactoryBase instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemFactoryBase instance) {
        if (ItemFactoryBase.instance == null) {
            synchronized (MUTEX) {
                if (ItemFactoryBase.instance == null) {
                    ItemFactoryBase.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemFactoryBase getInstance() {
        return instance;
    }

    public abstract ItemStack createStack(OilItem item, Player player, int size);
}
