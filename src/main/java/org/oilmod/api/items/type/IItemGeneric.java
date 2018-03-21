package org.oilmod.api.items.type;

public interface IItemGeneric {
     default ItemType<?, ?> getItemType() {return ItemType.GENERIC;}

}
