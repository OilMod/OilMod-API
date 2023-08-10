package org.oilmod.api.unification;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.registry.KeySettableBase;
import org.oilmod.api.unification.material.UniMaterial;
import org.oilmod.api.util.DeferredProvider;

import java.util.Collections;
import java.util.Set;

public class UniRequest<Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> extends KeySettableBase {
    private final Class<? extends Expressable> expressableClass;
    private final Class<? extends Expression> expressionClass;

    private final Set<DeferredProvider<UniMaterial>> entryPoints = new ObjectOpenHashSet<>();
    private Builder builder;
    private String id_prefix;

    public UniRequest(Class<? extends Expressable> expressableClass, Class<? extends Expression> expressionClass, String id_prefix) {
        this.expressableClass = expressableClass;
        this.expressionClass = expressionClass;
        this.id_prefix = id_prefix;
    }

    @SafeVarargs
    public final UniRequest<Expressable, Expression, Builder> entryPoint(DeferredProvider<UniMaterial>... material) {
        Validate.noNullElements(material);
        Collections.addAll(entryPoints, material);
        return this;
    }
    public UniRequest<Expressable, Expression, Builder> builder(Builder builder) {
        this.builder = builder;
        return this;
    }

    public Builder getBuilder() {
        return builder;
    }

    public Class<? extends Expressable> getExpressableClass() {
        return expressableClass;
    }

    public Class<? extends Expression> getExpressionClass() {
        return expressionClass;
    }

    public Set<DeferredProvider<UniMaterial>> getEntryPoints() {
        return entryPoints;
    }

    public String getIdPrefix() {
        return id_prefix;
    }
}
