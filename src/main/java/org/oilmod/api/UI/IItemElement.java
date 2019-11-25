package org.oilmod.api.UI;

public interface IItemElement extends IUIElement {

    void handle(IItemRef handle);
    int getRows();
    int getColumns();
    int toIndex(int row, int column);

    @Override
    default int getWidth() {
        return UIMPI.getSizeSlots()*getColumns();
    }

    @Override
    default int getHeight() {
        return UIMPI.getSizeSlots()*getRows();
    }
}
