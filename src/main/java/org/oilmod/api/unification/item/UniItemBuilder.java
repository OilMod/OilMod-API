package org.oilmod.api.unification.item;

import org.oilmod.api.unification.ExpressionBuilderBase;
import org.oilmod.api.unification.IExpressionBuilder;
import org.oilmod.api.unification.material.UniMaterialWrapper;

import java.util.concurrent.Future;

public class UniItemBuilder extends ExpressionBuilderBase<UniItem> {
    protected UniItemBuilder(UniMaterialWrapper uniMaterial) {
        super(uniMaterial);
    }

    @Override
    public UniItemBuilder clone() {
        return (UniItemBuilder) super.clone();
    }

    @Override
    protected void register() {

    }

    @Override
    protected UniItem createExpression() {
        return null;
    }
}
