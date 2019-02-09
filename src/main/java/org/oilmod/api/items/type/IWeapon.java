package org.oilmod.api.items.type;

public interface IWeapon extends IUnique {

    default int getMaxStackSize() {
        return 1;
    }
}
