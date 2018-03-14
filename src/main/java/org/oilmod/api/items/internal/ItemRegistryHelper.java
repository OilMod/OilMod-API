package org.oilmod.api.items.internal;

import org.oilmod.api.items.ItemRegistry;
import org.oilmod.api.items.OilItem;

/**
 * Internal - should not be called by user code
 */
public abstract class ItemRegistryHelper {
    private static ItemRegistryHelper instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";
    private static final ModItemSetterHelper HELPER = new ModItemSetterHelper();

    public static void setInstance(ItemRegistryHelper instance) {
        if (ItemRegistryHelper.instance == null) {
            synchronized (MUTEX) {
                if (ItemRegistryHelper.instance == null) {
                    ItemRegistryHelper.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    private static class ModItemSetterHelper extends org.oilmod.api.items.ModItemSetterHelper {
        @Override
        public void set(Object nmsItem, OilItem apiItem) {
            super.set(nmsItem, apiItem);
        }
    }

    public interface InitRegisterCallback {
        void callback(boolean success, Object nmsObject);
    }

    public static ItemRegistryHelper getInstance() {
        return instance;
    }

    protected void setNMSModItem(Object nmsItem, OilItem apiItem) {
        HELPER.set(nmsItem, apiItem);
    }

    public abstract <T extends OilItem> void register(ItemRegistry register, T apiItem);


    public abstract void initRegister(ItemRegistry register, InitRegisterCallback callback);

}
