package org.oilmod.api.items;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class ItemRegistry extends RegistryBase<OilItem, ItemRegistry, ItemRegistry.MPI, ItemRegistry.Helper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected ItemRegistry(OilMod mod, Helper<?> registryHelper) {
        super(mod, registryHelper, "oilitem");
    }


    /**
     * Internal - should not be called by user code
     */
    public static abstract class Helper<Impl extends Helper<Impl>> extends RegistryHelperBase<OilItem, ItemRegistry, MPI, Helper<?>, Impl> {
        private static Helper instance;

        @Override
        public void onReady() {
            super.onReady();
            instance = this;
        }

        public static Helper getInstance() {
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


    public static final class MPI extends RegistryMPIBase<OilItem, ItemRegistry, MPI, Helper<?>> {

    }
}
