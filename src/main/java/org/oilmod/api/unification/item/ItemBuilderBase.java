package org.oilmod.api.unification.item;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.unification.ExpressionBuilderBase;
import org.oilmod.api.unification.IExpression;
import org.oilmod.api.unification.material.UniMaterialWrapper;

public abstract class ItemBuilderBase<Expression extends OilItem & IExpression, Builder extends ItemBuilderBase<Expression, Builder>> extends ExpressionBuilderBase<Expression, Builder> {


}
