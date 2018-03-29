package org.oilmod.api.items.type;

public interface IUnique extends IItemGeneric {
    default int getMaxStackSize() {
        return 1;
    }
}
