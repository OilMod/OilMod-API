package org.oilmod.api.items.type;

public interface ISword extends IWeapon, IToolBlockBreaking {
    ItemType<? extends ISword, ? extends ISword> getItemType();
}