package org.oilmod.api.unification;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import org.oilmod.api.unification.material.UniMaterial;

import java.util.Set;

public abstract class ExpressableBase<Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> implements IExpressable<Expression, Builder> {
    private final TMap<UniMaterial, Expression> expressionMap = new THashMap<>();
    private TMap<UniMaterial, Builder> builderMap = new THashMap<>();
    public final Set<ISelector> selectors = new THashSet<>();
    public final Set<ISelector> antiselectors = new THashSet<>();


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
