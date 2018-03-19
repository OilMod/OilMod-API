package org.oilmod.api.items.type;

public interface IShovel extends IToolBlockBreaking {
    ItemType<? extends IShovel, ? extends IShovel> getItemType();
}
