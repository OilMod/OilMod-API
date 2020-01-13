package org.oilmod.api.items.type;

public interface IUnique extends IItemGeneric, IItemSpecialised {
    default int getMaxStackSize() {
        return 1;
    }

    @Override
    default ItemImplementationProvider getImplementationProvider() {
        return  ItemImplementationProvider.CUSTOM.getValue();
    }
}
