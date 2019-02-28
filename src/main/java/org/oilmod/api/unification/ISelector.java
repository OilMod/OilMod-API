package org.oilmod.api.unification;

import org.oilmod.api.unification.material.UniMaterial;

public interface ISelector {
    boolean isApplicable(UniMaterial mat);
    Iterable<UniMaterial> getCurrent();
}
