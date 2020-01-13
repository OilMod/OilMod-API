package org.oilmod.api.items.type;

public interface IFood extends IConsumable {


    default ItemImplementationProvider getImplementationProvider() {
        return  ItemImplementationProvider.FOOD.getValue();
    }
}
