package org.oilmod.api.items.type;

public interface IFood extends IConsumable {
    ItemType<? extends IFood, ? extends IFood> getItemType();
}
