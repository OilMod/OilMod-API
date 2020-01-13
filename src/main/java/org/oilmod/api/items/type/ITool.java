package org.oilmod.api.items.type;

public interface ITool extends IUnique {
    default ItemImplementationProvider getImplementationProvider() {
        return  ItemImplementationProvider.TOOL_CUSTOM.getValue();
    }
}
