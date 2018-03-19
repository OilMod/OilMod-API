package org.oilmod.api.items.type;

public interface IChestplate extends IArmor {
    ItemType<? extends IChestplate, ? extends IChestplate> getItemType();
}
