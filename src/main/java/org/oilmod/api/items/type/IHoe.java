package org.oilmod.api.items.type;

public interface IHoe extends ITool, IItemGeneric {


    default ImplementationProvider getImplementationProvider() {
        return ImplementationProvider.HOE;
    }
}
