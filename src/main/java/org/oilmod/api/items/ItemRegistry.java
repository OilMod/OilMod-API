package org.oilmod.api.items;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.Registry;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class ItemRegistry extends Registry<OilItem, ItemRegistry, ItemRegistry.RegistryMPI, ItemRegistry.RegistryHelper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected ItemRegistry(OilMod mod, RegistryHelper<?> registryHelper) {
        super(mod, registryHelper);
    }


    @Override
    public String getRegistryContext() {
        return "oilitem";
    }

    /**
     * Internal - should not be called by user code
     */
    public static abstract class RegistryHelper<Impl extends RegistryHelper<Impl>> extends RegistryHelperBase<OilItem, ItemRegistry, RegistryMPI, RegistryHelper<?>, Impl> {
        private static RegistryHelper instance;

        @Override
        public void onReady() {
            super.onReady();
            instance = this;
        }

        public static RegistryHelper getInstance() {
            return instance;
        }

        @Override
        public ItemRegistry create(OilMod mod) {
            return new ItemRegistry(mod, this);
        }


        protected void setNMSModItem(Object nmsItem, OilItem apiItem) {
            apiItem.setNmsItem(nmsItem);
        }
    }


    public static final class RegistryMPI extends RegistryMPIBase<OilItem, ItemRegistry, RegistryMPI, RegistryHelper<?>> {

    }
}
