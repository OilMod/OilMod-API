package org.oilmod.api.items.type;

public interface IWeapon extends IItemGeneric {
    ItemType<? extends IWeapon, ? extends IWeapon> getItemType();
}
