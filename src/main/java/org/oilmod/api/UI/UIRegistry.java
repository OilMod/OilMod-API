package org.oilmod.api.UI;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;

public class UIRegistry extends RegistryBase<UIFactory<?>, UIRegistry, UIRegistry.RegistryMPI, UIRegistry.RegistryHelper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected UIRegistry(OilMod mod, RegistryHelper<?> registryHelper) {
        super(mod, registryHelper, "oilui");
    }

    public static final class RegistryMPI extends RegistryMPIBase<UIFactory<?>, UIRegistry, RegistryMPI, RegistryHelper<?>> {

    }
    public static abstract class RegistryHelper<Impl extends RegistryHelper<Impl>> extends RegistryHelperBase<UIFactory<?>, UIRegistry, RegistryMPI, RegistryHelper<?>, Impl> {
        @Override
        public UIRegistry create(OilMod mod) {
            return new UIRegistry(mod, this);
        }
    }
}
