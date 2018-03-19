package org.oilmod.api.items.type;

public interface IConsumable extends IItemGeneric {
    ItemType<? extends IConsumable, ? extends IConsumable> getItemType();
}
