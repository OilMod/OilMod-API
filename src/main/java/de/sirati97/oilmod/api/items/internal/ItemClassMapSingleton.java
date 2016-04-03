package de.sirati97.oilmod.api.items.internal;

import de.sirati97.oilmod.api.items.OilItemBase;

/**
 * Created by sirati97 on 02.04.2016.
 */
public abstract class ItemClassMapSingleton {
    private static ItemClassMapSingleton instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemClassMapSingleton instance) {
        if (ItemClassMapSingleton.instance == null) {
            synchronized (MUTEX) {
                if (ItemClassMapSingleton.instance == null) {
                    ItemClassMapSingleton.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemClassMapSingleton getInstance() {
        return instance;
    }

    public abstract <T extends OilItemBase> T[] getOilItemsByClass(Class<T> clazz);
}
