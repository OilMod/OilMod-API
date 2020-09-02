package org.oilmod.api.unification;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.registry.SimpleRegistryMPIBase;

public class UnificationRegistry extends RegistryBase<UniRequest<?>, UnificationRegistry, UnificationRegistry.MPI, UnificationRegistry.Helper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected UnificationRegistry(OilMod mod, Helper<?> registryHelper) {
        super(mod, registryHelper, "UniRequest<?>");
    }


    /**
     * Internal - should not be called by user code
     */
    public static abstract class Helper<Impl extends Helper<Impl>> extends RegistryHelperBase<UniRequest<?>, UnificationRegistry, MPI, Helper<?>, Impl> {
        private static Helper instance;

        @Override
        public void onReady() {
            super.onReady();
            instance = this;
        }

        public static <Impl extends Helper<Impl>> Impl getInstance() {
            return (Impl)(Object)instance;
        }

        @Override
        public UnificationRegistry create(OilMod mod) {
            return new UnificationRegistry(mod, this);
        }
    }

    public static class DefaultHelper extends Helper<DefaultHelper>{}
    public static final class MPI extends SimpleRegistryMPIBase<UniRequest<?>, UnificationRegistry, MPI, Helper<?>> {
        @Override
        public Helper<?> createDefaultProvider() {
            return new DefaultHelper();
        }
    }
}