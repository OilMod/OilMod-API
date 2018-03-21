package org.oilmod.api.items.type;

public interface ITool extends IItemGeneric {
    default ItemType<? extends ITool, ? extends ITool> getItemType() {return ItemType.TOOL;}
}
