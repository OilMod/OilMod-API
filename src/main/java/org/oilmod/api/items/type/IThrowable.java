package org.oilmod.api.items.type;

public interface IThrowable extends IItemGeneric {
    ItemType<? extends IThrowable, ? extends IThrowable> getItemType();
}
