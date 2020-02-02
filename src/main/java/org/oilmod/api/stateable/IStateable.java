package org.oilmod.api.stateable;

public interface IStateable {
    default boolean hasEnumerableState() {
        return false;
    }
    default boolean hasComplexState(IState state) {
        return false;
    }

}
