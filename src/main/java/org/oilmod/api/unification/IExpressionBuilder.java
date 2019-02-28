package org.oilmod.api.unification;

import org.oilmod.api.unification.material.UniMaterialWrapper;

import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface IExpressionBuilder<Expression extends IExpression> extends Cloneable {
    UniMaterialWrapper getUniMaterial();
    IExpressionBuilder<Expression> clone();

    void build();
    void build(Consumer<Expression> future);

    /**
     * only to be called internally. Should invoke all waiting consumers
     * @return
     */
    Expression implement();
}
