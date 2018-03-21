package org.oilmod.api.items.type;

public interface ISword extends IWeapon, IToolBlockBreaking {
    default ItemType<? extends ISword, ? extends ISword> getItemType() {return ItemType.SWORD;}
}