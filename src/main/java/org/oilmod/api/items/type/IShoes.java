package org.oilmod.api.items.type;

public interface IShoes extends IArmor {
    default ItemType<? extends IShoes, ? extends IShoes> getItemType() {return ItemType.SHOES;}
}
