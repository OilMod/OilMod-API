package org.oilmod.api.items.type;

public interface IAxe extends IToolBlockBreaking {
    default ItemType<? extends IAxe, ? extends IAxe> getItemType() {return ItemType.AXE;}
}
