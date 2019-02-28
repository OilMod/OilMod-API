package org.oilmod.api.unification;

import org.oilmod.api.unification.material.UniMaterial;

public interface IExpressable<Expression extends IExpression> {
    boolean isApplicable(UniMaterial mat);
    boolean hasExpression(UniMaterial mat);
    Expression getExpression(UniMaterial mat);
    IExpressionBuilder<Expression> addExpression(UniMaterial mat);
    IExpressionBuilder<Expression> getStandardBuilder(UniMaterial mat);
}
