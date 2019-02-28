package org.oilmod.api.unification;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import org.oilmod.api.unification.material.UniMaterial;

import java.util.Set;

public abstract class ExpressableBase<Expression extends IExpression> implements IExpressable<Expression> {
    private final TMap<UniMaterial, Expression> expressionMap = new THashMap<>();
    private final TMap<UniMaterial, IExpressionBuilder<Expression>> builderMap = new THashMap<>();
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
    public IExpressionBuilder<Expression> addExpression(UniMaterial mat) {
        return builderMap.computeIfAbsent(mat, material -> getStandardBuilder(material).clone());
    }

}
