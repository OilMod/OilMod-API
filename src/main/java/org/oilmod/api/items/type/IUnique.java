package org.oilmod.api.items.type;

public interface IUnique extends IItemGeneric, IItemSpecialised {
    default int getMaxStackSize() {
        return 1;
    }

    @Override
    default ImplementationProvider getImplementationProvider() {
        return ImplementationProvider.CUSTOM;
    }
}
