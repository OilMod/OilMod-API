package org.oilmod.api.items.internal;

import org.oilmod.api.items.OilItem;

/**
 * Internal
 */
public abstract class ItemClassMap {
    private static ItemClassMap instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemClassMap instance) {
        if (ItemClassMap.instance == null) {
            synchronized (MUTEX) {
                if (ItemClassMap.instance == null) {
                    ItemClassMap.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemClassMap getInstance() {
        return instance;
    }

    public abstract <T extends OilItem> T[] getOilItemsByClass(Class<T> clazz);
}
