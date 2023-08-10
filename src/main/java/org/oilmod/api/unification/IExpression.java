package org.oilmod.api.unification;

import org.oilmod.api.unification.material.IUniMaterial;
import org.oilmod.api.unification.material.UniMaterial;
import org.oilmod.api.unification.material.UniMaterialWrapper;

public interface IExpression {
    IUniMaterial getUniMaterial();
}
