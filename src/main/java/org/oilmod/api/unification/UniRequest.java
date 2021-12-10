package org.oilmod.api.unification;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.oilmod.api.registry.KeySettableBase;
import org.oilmod.api.unification.item.UniItemBuilder;
import org.oilmod.api.unification.material.UniMaterial;

import java.util.Collections;
import java.util.Set;

public class UniRequest<Expressable extends IExpressable<Expression, Builder>, Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> extends KeySettableBase {

    private final Set<UniMaterial> entryPoints = new ObjectOpenHashSet<>();
    private Builder builder;

    public UniRequest<Expressable, Expression, Builder> entryPoint(UniMaterial... material) {
        Collections.addAll(entryPoints, material);
        return this;
    }
    public UniRequest<Expressable, Expression, Builder> builder(Builder builder) {
        this.builder = builder;
        return this;
    }

}
