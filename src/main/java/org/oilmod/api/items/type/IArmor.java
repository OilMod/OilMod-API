package org.oilmod.api.items.type;

public interface IArmor extends IItemGeneric {
    ItemType<? extends IArmor, ? extends IArmor> getItemType();
}
