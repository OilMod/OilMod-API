package org.oilmod.api.unification;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.oilmod.api.OilMod;
import org.oilmod.api.registry.DeferredRegister;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.SimpleRegistryMPIBase;
import org.oilmod.api.unification.material.IUniMaterial;
import org.oilmod.api.unification.material.MaterialBuilder;
import org.oilmod.api.util.DeferredProvider;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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


//    public <Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>>
//    void register(String id, Class<? extends Expressable> expressableClass, Class<? extends Expression> expressionClass, Supplier<Builder> builder, LazySingletonArray<UniMaterial> entrypoints) {
//        register(id, expressableClass, expressionClass, builder, (LazyArray<UniMaterial>)entrypoints);
//    }
//
//    public <Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>>
//    void register(String id, Class<? extends Expressable> expressableClass, Class<? extends Expression> expressionClass,  Supplier<Builder> builder, LazySingletonArray<UniMaterial> entrypoints, Function<UniRequest<Expressable, Expression, Builder>, UniRequest<Expressable, Expression, Builder>> requestPopulator) {
//        register(id, expressableClass, expressionClass, builder, (LazyArray<UniMaterial>)entrypoints, requestPopulator);
//    }
//
//    public <Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>>
//    void register(String id, Class<? extends Expressable> expressableClass, Class<? extends Expression> expressionClass, Supplier<Builder> builder, LazyArray<UniMaterial> entrypoints) {
//        register(id, expressableClass, expressionClass, (r) -> (r.builder(builder.get()).entryPoint(entrypoints.get())));
//    }
//
//    public <Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>>
//    void register(String id, Class<? extends Expressable> expressableClass, Class<? extends Expression> expressionClass,  Supplier<Builder> builder, LazyArray<UniMaterial> entrypoints, Function<UniRequest<Expressable, Expression, Builder>, UniRequest<Expressable, Expression, Builder>> requestPopulator) {
//        register(id, expressableClass, expressionClass, requestPopulator.compose((r) -> (r.builder(builder.get()).entryPoint(entrypoints.get()))));
//    }

    public <Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>>
        void register(String id, Class<? extends Expressable> expressableClass, Class<? extends Expression> expressionClass, Function<UniRequest<Expressable, Expression, Builder>, UniRequest<Expressable, Expression, Builder>> requestPopulator) {
        register(id, () -> requestPopulator.apply(new UniRequest<>(expressableClass, expressionClass, id + "_")));
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
            Map<String, MaterialBuilder> builders = new Object2ObjectOpenHashMap<>();

            for(UniRequest<?,?,?> request:getRegistered()) {
                IExpressionBuilder b = request.getBuilder();
                Set<IUniMaterial> variants = request.getEntryPoints().stream()
                        .map(DeferredProvider::get)
                        .flatMap(IUniMaterial::getVariants)
                        .collect(Collectors.toSet());
                for (IUniMaterial variant : variants) {
                    DeferredRegister[] defRegs = b.register(null, variant, request.getIdPrefix() + variant.getMainIdentifier());
                    for (DeferredRegister defReg:defRegs) {
                        Standard.mod.addDeferredRegister(defReg);
                    }
                }
            }
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