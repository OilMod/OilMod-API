package org.oilmod.api.unification;

import org.oilmod.api.unification.material.UniMaterial;

/**
 * An Expressable operates over all childs (specialisations) of a UniMaterial that dont have specialisations themselves and adds the expression
 * @param <Expression>
 * @param <Builder>
 */
public interface IExpressable<Expression extends IExpression, Builder extends IExpressionBuilder<Expression, Builder>> {
    boolean isApplicable(UniMaterial mat);
    boolean hasExpression(UniMaterial mat);
    Expression getExpression(UniMaterial mat);
    Builder addExpression(UniMaterial mat);
    Builder createStandardBuilder(UniMaterial mat);
    void freeze();
    boolean isFrozen();
}
