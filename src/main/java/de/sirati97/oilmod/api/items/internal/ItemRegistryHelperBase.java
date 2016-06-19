package de.sirati97.oilmod.api.items.internal;

import de.sirati97.oilmod.api.items.ItemRegistry;
import de.sirati97.oilmod.api.items.OilItemBase;

/**
 * Internal - shoculd not be called by user code
 */
public abstract class ItemRegistryHelperBase {
    private static ItemRegistryHelperBase instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";
    private static final ModItemSetterHelper HELPER = new ModItemSetterHelper();

    public static void setInstance(ItemRegistryHelperBase instance) {
        if (ItemRegistryHelperBase.instance == null) {
            synchronized (MUTEX) {
                if (ItemRegistryHelperBase.instance == null) {
                    ItemRegistryHelperBase.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    private static class ModItemSetterHelper extends de.sirati97.oilmod.api.items.ModItemSetterHelper {
        @Override
        public void set(Object nmsItem, OilItemBase apiItem) {
            super.set(nmsItem, apiItem);
        }
    }

    public interface InitRegisterCallback {
        void callback(boolean success, Object nmsObject);
    }

    public static ItemRegistryHelperBase getInstance() {
        return instance;
    }

    protected void setNMSModItem(Object nmsItem, OilItemBase apiItem) {
        HELPER.set(nmsItem, apiItem);
    }

    public abstract <T extends OilItemBase> void register(ItemRegistry register, T apiItem);


    public abstract void initRegister(ItemRegistry register, InitRegisterCallback callback);

}
