package org.oilmod.api.unification;

import org.oilmod.api.unification.material.UniMaterial;

public interface IExpressable<Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> {
    boolean isApplicable(UniMaterial mat);
    boolean hasExpression(UniMaterial mat);
    Expression getExpression(UniMaterial mat);
    Builder addExpression(UniMaterial mat);
    Builder createStandardBuilder(UniMaterial mat);
    void freeze();
    boolean isFrozen();
}
