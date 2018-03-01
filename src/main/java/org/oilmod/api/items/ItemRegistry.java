package org.oilmod.api.items;

import org.oilmod.api.items.internal.ItemRegistryHelperBase;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class ItemRegistry {
    private String id;
    private boolean registered;
    private Object nmsObject;

    /**
     * Creates new instance of ItemRegistry
     * @param id Short unique id. Do not change it later, otherwise your plugin becomes incompatible with older versions of you mod
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

    /**
     *
     * @return the id of you mod
     */
    public String getId() {
        return id;
    }

    /**
     * Registers an Item for your mod
     */
    public <T extends OilItemBase> void register(T apiItem) {
        if (!registered) {
            throw new IllegalStateException("ItemRegister was not successfully initialized");
        } else if (apiItem.isEnchantable() && apiItem.getEnchantSelectModifier() < 1) {
            throw new IllegalStateException("Item cannot be registered because EnchantSelectModifier is < 1 while item is enchantable");
        }
        ItemRegistryHelperBase.getInstance().register(this, apiItem);
    }

    /**
     *
     * @return nms version of this object
     */
    public Object getNmsObject() {
        return nmsObject;
    }
}
