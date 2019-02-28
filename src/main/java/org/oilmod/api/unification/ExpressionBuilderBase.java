package org.oilmod.api.unification;

import org.oilmod.api.unification.item.UniItemBuilder;
import org.oilmod.api.unification.material.UniMaterialWrapper;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public abstract class ExpressionBuilderBase<Expression extends IExpression> implements IExpressionBuilder<Expression> {
    private final UniMaterialWrapper uniMaterial;
    private final Set<Consumer<Expression>> futures = new HashSet<>();

    protected ExpressionBuilderBase(UniMaterialWrapper uniMaterial) {
        this.uniMaterial = uniMaterial;
    }

    @Override
    public UniMaterialWrapper getUniMaterial() {
        return uniMaterial;
    }

    @Override
    public IExpressionBuilder<Expression> clone() {
        try {
            //noinspection unchecked
            return (IExpressionBuilder<Expression>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void build() {
        register();
    }

    @Override
    public void build(Consumer<Expression> future) {
        futures.add(future);
        build();
    }

    @Override
    public Expression implement() {
        Expression result = createExpression();
        futures.forEach(f -> f.accept(result));
        return result;
    }

    protected abstract void register();
    protected abstract Expression createExpression();

}
