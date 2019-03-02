package org.oilmod.api.unification;

import org.oilmod.api.unification.material.UniMaterialWrapper;

import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IExpressionBuilder<Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> extends Cloneable {
    UniMaterialWrapper getMaterial();
    Builder material(UniMaterialWrapper material);
    Builder factory(Function<Builder, Expression> factory);
    Builder clone();


    void build();
    void build(Consumer<Expression> future);

    /**
     * only to be called internally. Should invoke all waiting consumers
     * @return
     */
    Expression implement();
}
