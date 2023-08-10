package org.oilmod.api.unification;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.oilmod.api.registry.DeferredObject;
import org.oilmod.api.registry.DeferredRegister;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.unification.material.IUniMaterial;
import org.oilmod.api.unification.material.UniMaterialWrapper;

import java.util.Set;
import java.util.function.Consumer;

import static org.oilmod.util.LamdbaCastUtils.cast;

public abstract class ExpressionBuilderBase<Expression extends IExpression, Builder extends ExpressionBuilderBase<Expression, Builder>> implements IExpressionBuilder<Expression, Builder> {
    private UniMaterialWrapper material;
    private Consumer<DeferredObject<? extends Expression>> futures = (__) -> {};


    @Override
    public void subscribe(Consumer<DeferredObject<? extends Expression>> future) {
        futures =  futures.andThen(future);
    }

    @Override
    public <TReg extends RegistryBase<? super Expression, TReg, ?, ?>> DeferredRegister<Expression, TReg>[] register(IUniMaterial type, IUniMaterial base, String id) {
        return new DeferredRegister[] {
                new DeferredRegister<>(Standard.mod.createKey(id), cast(getRegistry()), () -> implement(type, base), futures)
        };
    }


    protected abstract Expression implement(IUniMaterial type, IUniMaterial base);
    protected Builder builder() {
        //noinspection unchecked
        return cast(this);
    }

}
