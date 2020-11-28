package org.oilmod.api.UI;

public class ElementBase implements  IUIElement {


    @Override
    public int getLeft() {
        return 0;
    }

    @Override
    public int getTop() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public  boolean withinBorder() {
        return true;
    }
}
