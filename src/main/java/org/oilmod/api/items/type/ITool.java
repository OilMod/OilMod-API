package org.oilmod.api.items.type;

public interface ITool extends IItemGeneric {
    ItemType<? extends ITool, ? extends ITool> getItemType();
}
