package org.oilmod.api.UI;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.Collections;
import java.util.List;

public class UI<TContext> {
    private List<IUIElement> uiElements = new ObjectArrayList<>();
    private List<IUIElement> uiElementsReadOnly = Collections.unmodifiableList(uiElements);
    private List<IItemElement> itemElements = new ObjectArrayList<>();
    private List<IItemElement> itemElementsReadOnly = Collections.unmodifiableList(itemElements);
    private final UIFactory<TContext> factory;
    private final TContext context;
    private int topHeight;
    private int topWidth;
    private int widthHeight;
    private int widthWidth;

    public UI(UIFactory<TContext> factory, TContext context) {
        this.factory = factory;
        this.context = context;
    }

    public List<IItemElement> getItemElements() {
        return itemElementsReadOnly;
    }

    public List<IUIElement> getUiElements() {
        return uiElementsReadOnly;
    }

    public void addElement(IUIElement element) {
        uiElements.add(element);
        if (element instanceof IItemElement)itemElements.add((IItemElement) element);
    }

    public UIFactory getFactory() {
        return factory;
    }


    public int getTopHeight() {
        return topHeight;
    }

    public int getTopWidth() {
        return topWidth;
    }

    public int getWidthHeight() {
        return widthHeight;
    }

    public int getWidthWidth() {
        return widthWidth;
    }

    public void updateSize() {
        int height = 0;
        int width = 0;
        for (IUIElement element:uiElements) {
            if (!element.withinBorder())continue;
            height = Math.max(height, element.getTop() + element.getHeight());
            width = Math.max(width, element.getLeft() + element.getWidth());
        }
        topHeight = height;
        topWidth = width;
    }

    public TContext getContext() {
        return context;
    }
}
