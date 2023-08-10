package org.oilmod.api.unification;

import org.oilmod.api.registry.DeferredObject;
import org.oilmod.api.registry.RegistryBase;
import org.oilmod.api.registry.DeferredRegister;
import org.oilmod.api.unification.material.IUniMaterial;

import java.util.function.Consumer;

public interface IExpressionBuilder<Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> {


    void subscribe(Consumer<DeferredObject<? extends Expression>> future);

    Class<? extends RegistryBase<?, ?, ?, ?>> getRegistry();
    /**
     * only to be called internally. Should invoke all waiting consumers
     * @return
     */
    <TReg extends RegistryBase<? super Expression, TReg, ?, ?>> DeferredRegister<Expression, TReg>[] register(IUniMaterial type, IUniMaterial base, String id);
}
