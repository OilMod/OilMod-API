package org.oilmod.api.UI;

import org.oilmod.api.UI.slot.ISlotType;

public interface IItemElement extends IUIElement {

    void handle(IItemRef handle);
    int getRows();
    int getColumns();
    int toIndex(int row, int column);
    ISlotType getSlotType();

    @Override
    default int getWidth() {
        return UIMPI.getSizeSlots()*getColumns();
    }

    @Override
    default int getHeight() {
        return UIMPI.getSizeSlots()*getRows();
    }
}
