package org.oilmod.api.stateable.complex;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.registry.SimpleRegistryMPIBase;

public class ComplexStateTypeRegistry extends RegistryBase<ComplexStateType, ComplexStateTypeRegistry, ComplexStateTypeRegistry.RegistryMPI, ComplexStateTypeRegistry.RegistryHelper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item filter registry
     * @param registryHelper
     */
    protected ComplexStateTypeRegistry(OilMod mod, RegistryHelper<?> registryHelper) {
        super(mod, registryHelper, "complex_state_type");
    }

    public static final class RegistryMPI extends SimpleRegistryMPIBase<ComplexStateType, ComplexStateTypeRegistry, RegistryMPI, RegistryHelper<?>> {
        @Override
        public DefaultRegistryHelper createDefaultProvider() {
            return new DefaultRegistryHelper();
        }

        private static class DefaultRegistryHelper extends RegistryHelper<DefaultRegistryHelper>{}
    }
    public static abstract class RegistryHelper<Impl extends RegistryHelper<Impl>> extends RegistryHelperBase<ComplexStateType, ComplexStateTypeRegistry, RegistryMPI, RegistryHelper<?>, Impl> {
        @Override
        public ComplexStateTypeRegistry create(OilMod mod) {
            return new ComplexStateTypeRegistry(mod, this);
        }
    }
}
