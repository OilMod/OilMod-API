package org.oilmod.api.items.type;

public interface IHoe extends ITool {
    default ItemType<? extends IHoe, ? extends IHoe> getItemType() {return ItemType.HOE;}
}
