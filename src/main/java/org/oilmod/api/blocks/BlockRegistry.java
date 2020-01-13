package org.oilmod.api.blocks;


import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;

/**
 * Created by sirati97 on 16.01.2016.
 */
public class BlockRegistry extends RegistryBase<OilBlock, BlockRegistry, BlockRegistry.RegistryMPI, BlockRegistry.RegistryHelper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected BlockRegistry(OilMod mod, RegistryHelper<?> registryHelper) {
        super(mod, registryHelper, "oilblock");
    }


    /**
     * Internal - should not be called by user code
     */
    public static abstract class RegistryHelper<Impl extends RegistryHelper<Impl>> extends RegistryHelperBase<OilBlock, BlockRegistry, RegistryMPI, RegistryHelper<?>, Impl> {
        private static RegistryHelper<?> instance;

        @Override
        public void onReady() {
            super.onReady();
            instance = this;
        }

        public static RegistryHelper<?> getInstance() {
            return instance;
        }

        @Override
        public BlockRegistry create(OilMod mod) {
            return new BlockRegistry(mod, this);
        }


        protected void setNMSModBlock(Object nmsBlock, OilBlock apiItem) {
            apiItem.setNmsBlock(nmsBlock);
        }
    }


    public static final class RegistryMPI extends RegistryMPIBase<OilBlock, BlockRegistry, RegistryMPI, RegistryHelper<?>> {

    }
}
