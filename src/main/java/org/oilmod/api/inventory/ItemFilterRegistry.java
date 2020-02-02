package org.oilmod.api.inventory;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.registry.SimpleRegistryMPIBase;
import org.oilmod.api.stateable.complex.ComplexStateTypeRegistry;

public class ItemFilterRegistry extends RegistryBase<ItemFilter, ItemFilterRegistry, ItemFilterRegistry.RegistryMPI, ItemFilterRegistry.RegistryHelper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item filter registry
     * @param registryHelper
     */
    protected ItemFilterRegistry(OilMod mod, RegistryHelper<?> registryHelper) {
        super(mod, registryHelper, "itemfilter");
    }

    public static final class RegistryMPI extends SimpleRegistryMPIBase<ItemFilter, ItemFilterRegistry, RegistryMPI, RegistryHelper<?>> {
        @Override
        public DefaultRegistryHelper createDefaultProvider() {
            return new DefaultRegistryHelper();
        }

        private static class DefaultRegistryHelper extends RegistryHelper<DefaultRegistryHelper> {}
    }
    public static abstract class RegistryHelper<Impl extends RegistryHelper<Impl>> extends RegistryHelperBase<ItemFilter, ItemFilterRegistry, RegistryMPI, RegistryHelper<?>, Impl> {
        @Override
        public ItemFilterRegistry create(OilMod mod) {
            return new ItemFilterRegistry(mod, this);
        }
    }
}
