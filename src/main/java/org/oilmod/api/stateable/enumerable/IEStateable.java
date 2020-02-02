package org.oilmod.api.stateable.enumerable;

import org.oilmod.api.stateable.IStateable;

public interface IEStateable extends IStateable {
    IEnumerableState getDefaultEnumerableState();

    @Override
    default boolean hasEnumerableState() {
        return true;
    }
}
