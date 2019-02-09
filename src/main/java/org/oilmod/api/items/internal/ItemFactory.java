package org.oilmod.api.items.internal;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;

/**
 * Internal - should not be called by user code
 */
public abstract class ItemFactory {
    private static ItemFactory instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(ItemFactory instance) {
        if (ItemFactory.instance == null) {
            synchronized (MUTEX) {
                if (ItemFactory.instance == null) {
                    ItemFactory.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static ItemFactory getInstance() {
        return instance;
    }

    public abstract ItemStackRep createStack(OilItem item, EntityHumanRep player, int count, int data);
}
