package org.oilmod.api.UI;

public class FixedPos implements IPosition {
    private int left;
    private int top;

    public FixedPos(int left, int top) {
        this.left = left;
        this.top = top;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getTop() {
        return top;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
