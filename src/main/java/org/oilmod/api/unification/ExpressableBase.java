package org.oilmod.api.unification;


import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.oilmod.api.unification.material.UniMaterial;

import java.util.Map;
import java.util.Set;

public abstract class ExpressableBase<Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> implements IExpressable<Expression, Builder> {
    private final Map<UniMaterial, Expression> expressionMap = new Object2ObjectOpenHashMap<>();
    private Map<UniMaterial, Builder> builderMap = new Object2ObjectOpenHashMap<>();
    public final Set<ISelector> selectors = new ObjectOpenHashSet<>();
    public final Set<ISelector> antiselectors = new ObjectOpenHashSet<>();


    @Override
    public boolean isApplicable(UniMaterial mat) {
        return selectors.stream().anyMatch(sel -> sel.isApplicable(mat))
                && antiselectors.stream().noneMatch(sel -> sel.isApplicable(mat));
    }

    @Override
    public boolean hasExpression(UniMaterial mat) {
        return expressionMap.containsKey(mat) || builderMap.containsKey(mat);
    }

    @Override
    public Expression getExpression(UniMaterial mat) {
        return expressionMap.get(mat);
    }

    @Override
    public Builder addExpression(UniMaterial mat) {
        return builderMap.computeIfAbsent(mat, this::createStandardBuilder);
    }

    @Override
    public void freeze() {

        builderMap = null;
    }

    @Override
    public boolean isFrozen() {
        return builderMap != null;
    }
}
