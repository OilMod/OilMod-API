package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.items.internal.ItemRegistryHelperBase;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class ItemRegistry {
    private String id;
    private boolean registered;
    private Object nmsObject;

    /**
     *
     * @param id Short unique id. Do not change it later, otherwise your plugin becomes incompatible with older versions
     */
    public ItemRegistry(String id) {
        this.id = id;
        ItemRegistryHelperBase.getInstance().initRegister(this, new ItemRegistryHelperBase.InitRegisterCallback() {
            public void callback(boolean success, Object nmsObject) {
                registered = success;
                ItemRegistry.this.nmsObject = nmsObject;
            }
        });
    }

    public String getId() {
        return id;
    }

    public <T extends OilItemBase> void register(T apiItem) {
        if (!registered) {
            throw new IllegalStateException("ItemRegister was not successfully initialized");
        } else if (apiItem.isEnchantable() && apiItem.getEnchantSelectModifier() < 1) {
            throw new IllegalStateException("Item cannot be registered because EnchantSelectModifier is < 1 while item is enchantable");
        }
        ItemRegistryHelperBase.getInstance().register(this, apiItem);
    }

    public Object getNmsObject() {
        return nmsObject;
    }
}
