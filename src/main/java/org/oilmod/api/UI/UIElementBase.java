package org.oilmod.api.UI;


public abstract class UIElementBase implements IUIElement {
    private int posX;
    private int posY;

    public UIElementBase(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public int getLeft() {
        return posX;
    }

    @Override
    public int getTop() {
        return posY;
    }

}
