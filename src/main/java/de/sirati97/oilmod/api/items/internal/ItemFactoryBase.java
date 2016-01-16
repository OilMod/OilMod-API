package de.sirati97.oilmod.api.items.internal;

import de.sirati97.oilmod.api.items.OilItemBase;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by sirati97 on 15.01.2016.
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

    public abstract ItemStack createStack(OilItemBase item, Player player, int size);
}
