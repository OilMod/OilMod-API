package de.sirati97.oilmod.api.items;

import de.sirati97.oilmod.api.items.internal.ItemRegisterHelperBase;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class ItemRegister {
    private String id;
    private boolean registered;
    private Object nmsObject;

    /**
     *
     * @param id Short unique id. Do not change it later, otherwise your plugin becomes incompatible with older versions
     */
    public ItemRegister(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void register(OilItemBase apiItem) {
        if (!registered) throw new IllegalStateException("ItemRegister is not initialized");
        ItemRegisterHelperBase.getInstance().register(this, apiItem);
    }

    public void init() {
        if (registered) throw new IllegalStateException("ItemRegister is already initialized");
        ItemRegisterHelperBase.getInstance().initRegister(this, new ItemRegisterHelperBase.InitRegisterCallback() {
            public void callback(boolean success, Object nmsObject) {
                registered = success;
                ItemRegister.this.nmsObject = nmsObject;
            }
        });
    }

    public Object getNmsObject() {
        return nmsObject;
    }
}
