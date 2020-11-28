package org.oilmod.api.UI;

public class ScrollbarElement extends FlexDimElement {
    private int max;
    private int currentlyVisible = 1;
    private int current = 0;

    public ScrollbarElement(IPosition pos, ISize size, int max) {
        super(pos, size);
        this.max = max;
    }

    public ScrollbarElement(int left, int top, ISize size, int max) {
        super(left, top, size);
        this.max = max;
    }

    public ScrollbarElement(int left, int top, int width, int height, int max) {
        super(left, top, width, height);
        this.max = max;
    }

    public ScrollbarElement(IPosition pos, int width, int height, int max) {
        super(pos, width, height);
        this.max = max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setCurrentlyVisible(int currentlyVisible) {
        this.currentlyVisible = currentlyVisible;
    }

    public int getCurrentlyVisible() {
        return currentlyVisible;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public IPosition getScrollDependent(int leftOffset, double leftMulti, int topOffset, double topMulti) {
        return new IPosition() {
            @Override
            public int getLeft() {
                return leftOffset + (int) (leftMulti*getCurrent());
            }

            @Override
            public int getTop() {
                return topOffset + (int) (topMulti*getCurrent());
            }
        };
    }
}
