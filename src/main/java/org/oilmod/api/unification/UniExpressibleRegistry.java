package org.oilmod.api.unification;

import org.oilmod.api.OilMod;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.SimpleRegistryMPIBase;
import org.oilmod.api.unification.material.MaterialRequestBuilder;
import org.oilmod.api.unification.material.UniMaterial;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.oilmod.util.LamdbaCastUtils.cast;

public class UniExpressibleRegistry extends RegistryBase<UniRequest<?,?,?>, UniExpressibleRegistry, UniExpressibleRegistry.MPI, UniExpressibleRegistry.Helper<?>> {

    /**
     * Creates new instance of Registry
     *
     * @param mod            associated mod with this item registry
     * @param registryHelper
     */
    protected UniExpressibleRegistry(OilMod mod, Helper<?> registryHelper) {
        super(mod, registryHelper, "uni_request");
    }



    public <Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>>
        void register(String id, Class<? extends Expressable> expressableClass, Class<? extends Expression> expressionClass, Function<UniRequest<Expressable, Expression, Builder>, UniRequest<Expressable, Expression, Builder>> requestPopulator) {
        register(id, () -> requestPopulator.apply(new UniRequest<>()));
    }

    /**
     * Internal - should not be called by user code
     */
    public static abstract class Helper<Impl extends Helper<Impl>> extends RegistryHelperBase<UniRequest<?,?,?>, UniExpressibleRegistry, MPI, Helper<?>, Impl> {
        private static Helper<?> instance;

        @Override
        public void onReady() {
            super.onReady();
            instance = this;
        }

        public static <Impl extends Helper<Impl>> Impl getInstance() {
            return cast(instance);
        }

        @Override
        public UniExpressibleRegistry create(OilMod mod) {
            return new UniExpressibleRegistry(mod, this);
        }

        @Override
        protected void afterAllMods() {
            super.afterAllMods();
        }
    }

    public static class DefaultHelper extends Helper<DefaultHelper>{}
    public static final class MPI extends SimpleRegistryMPIBase<UniRequest<?,?,?>, UniExpressibleRegistry, MPI, Helper<?>> {
        @Override
        public Helper<?> createDefaultProvider() {
            return new DefaultHelper();
        }
    }
}