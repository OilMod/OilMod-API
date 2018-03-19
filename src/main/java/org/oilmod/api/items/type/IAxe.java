package org.oilmod.api.items.type;

public interface IAxe extends IToolBlockBreaking {
    ItemType<? extends IAxe, ? extends IAxe> getItemType();
}
