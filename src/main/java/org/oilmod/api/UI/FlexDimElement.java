package org.oilmod.api.UI;


public abstract class FlexDimElement extends FlexPosElement implements IFlexDimElement {
    private final ISize size;

    public FlexDimElement(IPosition pos, ISize size) {
        super(pos);
        this.size = size;
    }

    public FlexDimElement(int left, int top, ISize size) {
        this(new FixedPos(left, top), size);
    }

    public FlexDimElement(int left, int top, int width, int height) {
        this(left, top, new FixedSize(width, height));
    }

    public FlexDimElement(IPosition pos, int width, int height) {
        this(pos, new FixedSize(width, height));
    }

    @Override
    public ISize getSize() {
        return size;
    }
}
