package org.oilmod.api.items.type;

public interface IConsumable extends IItemGeneric {
    default ItemType<? extends IConsumable, ? extends IConsumable> getItemType() {return ItemType.CONSUMERABLE;}
}
