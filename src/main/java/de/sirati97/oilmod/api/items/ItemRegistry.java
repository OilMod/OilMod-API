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
    }

    public String getId() {
        return id;
    }

    public void register(OilItemBase apiItem) {
        if (!registered) throw new IllegalStateException("ItemRegister is not initialized");
        ItemRegistryHelperBase.getInstance().register(this, apiItem);
    }

    public void init() {
        if (registered) throw new IllegalStateException("ItemRegister is already initialized");
        ItemRegistryHelperBase.getInstance().initRegister(this, new ItemRegistryHelperBase.InitRegisterCallback() {
            public void callback(boolean success, Object nmsObject) {
                registered = success;
                ItemRegistry.this.nmsObject = nmsObject;
            }
        });
    }

    public Object getNmsObject() {
        return nmsObject;
    }
}
