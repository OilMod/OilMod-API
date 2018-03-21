package org.oilmod.api.items.type;

public interface IPickaxe extends IToolBlockBreaking {
    default ItemType<? extends IPickaxe, ? extends IPickaxe> getItemType() {return ItemType.PICKAXE;}
}
