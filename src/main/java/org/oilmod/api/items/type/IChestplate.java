package org.oilmod.api.items.type;

public interface IChestplate extends IArmor {
    default ItemType<? extends IChestplate, ? extends IChestplate> getItemType() {return ItemType.CHESTPLATE;}
}
