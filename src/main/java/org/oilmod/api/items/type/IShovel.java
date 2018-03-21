package org.oilmod.api.items.type;

public interface IShovel extends IToolBlockBreaking {
    default ItemType<? extends IShovel, ? extends IShovel> getItemType() {return ItemType.SHOVEL;}
}
