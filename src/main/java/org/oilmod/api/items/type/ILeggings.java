package org.oilmod.api.items.type;

public interface ILeggings extends IArmor {
    default ItemType<? extends ILeggings, ? extends ILeggings> getItemType() {return ItemType.LEGGINGS;}
}
