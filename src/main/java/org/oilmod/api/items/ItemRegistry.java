package org.oilmod.api.items;

import org.oilmod.api.OilMod;
import org.oilmod.api.items.internal.ItemRegistryHelperBase;

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
        ItemRegistryHelperBase.getInstance().initRegister(this, (success, nmsObject) -> {
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
