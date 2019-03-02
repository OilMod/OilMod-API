package org.oilmod.api.unification;

import org.oilmod.api.unification.material.UniMaterialWrapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ExpressionBuilderBase<Expression extends IExpression, Builder extends ExpressionBuilderBase<Expression, Builder>> implements IExpressionBuilder<Expression, Builder> {
    private UniMaterialWrapper material;
    private final Set<Consumer<Expression>> futures = new HashSet<>();
    private Function<Builder, Expression> factory = ExpressionBuilderBase::createExpression;

    protected ExpressionBuilderBase() {
        builder().blackhole();
    }
    final void blackhole() {}

    @Override
    public UniMaterialWrapper getMaterial() {
        return material;
    }

    @Override
    public Builder material(UniMaterialWrapper material) {
        this.material = material;
        return builder();
    }

    @Override
    public Builder factory(Function<Builder, Expression> factory) {
        this.factory = factory;
        return builder();
    }

    @Override
    public Builder clone() {
        try {
            //noinspection unchecked
            return (Builder) super.clone();
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
        Expression result = factory.apply(builder());
        futures.forEach(f -> f.accept(result));
        return result;
    }

    protected abstract void register();
    protected abstract Expression createExpression();
    protected Builder builder() {
        //noinspection unchecked
        return (Builder) this;
    }

}
