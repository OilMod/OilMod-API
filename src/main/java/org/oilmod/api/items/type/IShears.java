package org.oilmod.api.items.type;

public interface IShears extends IToolBlockBreaking {
    default ItemType<? extends IShears, ? extends IShears> getItemType() {return ItemType.SHEARS;}
}
