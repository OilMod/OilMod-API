package org.oilmod.api.items.type;

public interface IShoes extends IArmor {
    ItemType<? extends IShoes, ? extends IShoes> getItemType();
}
