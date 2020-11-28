package org.oilmod.api.UI;

public interface IFlexPosElement extends IUIElement {
    IPosition getPosition();

    @Override
    default int getLeft() {
        return getPosition().getLeft();
    }

    @Override
    default int getTop() {
        return getPosition().getTop();
    }
}
