package org.oilmod.api.items.type;

public interface IArmor extends IItemGeneric {
    default ItemType<? extends IArmor, ? extends IArmor> getItemType() {return ItemType.ARMOR;}
}
