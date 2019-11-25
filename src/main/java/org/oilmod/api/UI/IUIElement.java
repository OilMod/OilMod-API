package org.oilmod.api.UI;

public interface IUIElement {
    int getLeft();
    int getTop();
    int getWidth();
    int getHeight();
    default boolean withinBorder() {
        return true;
    }
}
