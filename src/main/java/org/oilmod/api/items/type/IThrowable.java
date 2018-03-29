package org.oilmod.api.items.type;

public interface IThrowable extends IItemGeneric {

    @Override
    default int getMaxStackSize() {
        return 16;
    }
}
