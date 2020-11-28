package org.oilmod.api.UI;

public interface IFlexSizeElement extends IUIElement {
    ISize getSize();

    @Override
    default int getWidth() {
        return getSize().getWidth();
    }

    @Override
    default int getHeight() {
        return getSize().getHeight();
    }
}
