package org.oilmod.api.items.type;

public interface ITool extends IUnique {
    default ImplementationProvider getImplementationProvider() {
        return ImplementationProvider.TOOL_CUSTOM;
    }
}
