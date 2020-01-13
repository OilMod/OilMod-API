package org.oilmod.api.items.type;

public interface IHoe extends ITool, IItemGeneric {


    default ItemImplementationProvider getImplementationProvider() {
        return  ItemImplementationProvider.HOE.getValue();
    }
}
