package org.oilmod.api.items.type;

public interface IWeapon extends IItemGeneric {
    default ItemType<? extends IWeapon, ? extends IWeapon> getItemType() {return ItemType.WEAPON;}
}
