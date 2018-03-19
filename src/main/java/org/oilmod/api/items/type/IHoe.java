package org.oilmod.api.items.type;

public interface IHoe extends ITool {
    ItemType<? extends IHoe, ? extends IHoe> getItemType();
}
