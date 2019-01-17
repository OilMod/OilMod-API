package org.oilmod.api.items.type;

public interface IThrowable extends IDispensableProjectile {

    @Override
    default int getMaxStackSize() {
        return 16;
    }
}
