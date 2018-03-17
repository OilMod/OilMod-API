package org.oilmod.api.items;

import org.oilmod.api.OilMod;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class ItemRegistry {
    private OilMod mod;
    private boolean registered;
    private Object nmsObject;

    /**
     * Creates new instance of ItemRegistry
     * @param mod associated mod with this item registry
     */
    public ItemRegistry(OilMod mod) {
        this.mod = mod;
        ItemRegistryHelper.getInstance().initRegister(this, (success, nmsObject) -> {
            registered = success;
            ItemRegistry.this.nmsObject = nmsObject;
        });
    }


    /**
     *
     * @return mod associated with this item registry
     */
    public OilMod getMod() {
        return mod;
    }

    /**
     * Registers an Item for your mod
     */
    public <T extends OilItem> void register(T apiItem) {
        if (!registered) {
            throw new IllegalStateException("ItemRegister was not successfully initialized");
        } else if (apiItem.isEnchantable() && apiItem.getEnchantSelectModifier() < 1) {
            throw new IllegalStateException("Item cannot be registered because EnchantSelectModifier is < 1 while item is enchantable");
        }
        ItemRegistryHelper.getInstance().register(this, apiItem);
    }

    /**
     *
     * @return nms version of this object
     */
    public Object getNmsObject() {
        return nmsObject;
    }

    /**
     * Internal - should not be called by user code
     */
    public static abstract class ItemRegistryHelper {
        private static ItemRegistryHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

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

        public interface InitRegisterCallback {
            void callback(boolean success, Object nmsObject);
        }

        public static ItemRegistryHelper getInstance() {
            return instance;
        }

        protected void setNMSModItem(Object nmsItem, OilItem apiItem) {
            apiItem.setNmsItem(nmsItem);
        }

        public abstract <T extends OilItem> void register(ItemRegistry register, T apiItem);


        public abstract void initRegister(ItemRegistry register, InitRegisterCallback callback);

    }
}
