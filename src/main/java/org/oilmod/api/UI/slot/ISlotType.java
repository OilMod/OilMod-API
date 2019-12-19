package org.oilmod.api.UI.slot;

public interface ISlotType {
    default boolean isNative() {return false;}
    boolean isSettable();
    boolean isTakeable();
}
