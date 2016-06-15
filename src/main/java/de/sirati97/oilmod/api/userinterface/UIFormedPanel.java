package de.sirati97.oilmod.api.userinterface;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIFormedPanel implements UIPanel{
    private final int height;
    private final int width;

    public UIFormedPanel(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int size() {
        return getHeight()*getWidth();
    }

    @Override
    public boolean hasForm() {
        return true;
    }

    @Override
    public final UIElementResult getUIElement(int index) {
        return _getUIElement(index%getWidth(), index/getWidth(), index);
    }

    public final UIElementResult getUIElement(int left, int top) {
        return _getUIElement(left, top, left+top*getWidth());
    }

    private UIElementResult _getUIElement(int left, int top, int rawIndex) {
        checkIndex(left, top, rawIndex);
        return getUIElement(left, top, rawIndex);
    }

    protected int toIndex(int left, int top) {
        int index = left+top*getWidth();
        checkIndex(left, top, index);
        return index;
    }

    private void checkIndex(int left, int top, int rawIndex) {
        if (left > getWidth()) {
            throw new IllegalArgumentException("Width has to be smaller than " + getWidth());
        }
        if (top > getHeight()) {
            throw new IllegalArgumentException("Height has to be smaller than " + getHeight());
        }
        if (rawIndex > size()) {
            throw new IllegalArgumentException("Index has to be smaller than " + size());
        }
    }

    protected abstract UIElementResult getUIElement(int left, int top, int rawIndex);
}
