package org.oilmod.api.items.type;

public interface IPickaxe extends IToolBlockBreaking {
    ItemType<? extends IPickaxe, ? extends IPickaxe> getItemType();
}
