package org.oilmod.api.inventory;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.Registry;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;

public class ItemFilterRegistry extends Registry<ItemFilter, ItemFilterRegistry, ItemFilterRegistry.RegistryMPI, ItemFilterRegistry.RegistryHelper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected ItemFilterRegistry(OilMod mod, RegistryHelper<?> registryHelper) {
        super(mod, registryHelper);
    }

    @Override
    public String getRegistryContext() {
        return "itemfilter";
    }

    public static final class RegistryMPI extends RegistryMPIBase<ItemFilter, ItemFilterRegistry, RegistryMPI, RegistryHelper<?>> {

    }
    public static abstract class RegistryHelper<Impl extends RegistryHelper<Impl>> extends RegistryHelperBase<ItemFilter, ItemFilterRegistry, RegistryMPI, RegistryHelper<?>, Impl> {
        @Override
        public ItemFilterRegistry create(OilMod mod) {
            return new ItemFilterRegistry(mod, this);
        }
    }
}
