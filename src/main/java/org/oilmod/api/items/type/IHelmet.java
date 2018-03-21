package org.oilmod.api.items.type;

public interface IHelmet extends IArmor {
    default ItemType<? extends IHelmet, ? extends IHelmet> getItemType() {return ItemType.HELMET;}
}
