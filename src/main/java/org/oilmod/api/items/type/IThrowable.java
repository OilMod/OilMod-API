package org.oilmod.api.items.type;

public interface IThrowable extends IItemGeneric {
    default ItemType<? extends IThrowable, ? extends IThrowable> getItemType() {return ItemType.THROWABLE;}
}
