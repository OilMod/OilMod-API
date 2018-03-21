package org.oilmod.api.items.type;

public interface IFood extends IConsumable {
    default ItemType<? extends IFood, ? extends IFood> getItemType() {return ItemType.FOOD;}
}
