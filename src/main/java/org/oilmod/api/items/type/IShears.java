package org.oilmod.api.items.type;

public interface IShears extends IToolBlockBreaking {
    ItemType<? extends IShears, ? extends IShears> getItemType();
}
