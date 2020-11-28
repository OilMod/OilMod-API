package org.oilmod.api.UI;


public abstract class FlexPosElement implements IFlexPosElement {
    private final IPosition position;

    public FlexPosElement(IPosition position) {
        this.position = position;
    }

    @Override
    public IPosition getPosition() {
        return position;
    }
}
