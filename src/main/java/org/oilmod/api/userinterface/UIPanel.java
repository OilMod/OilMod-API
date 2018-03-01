package org.oilmod.api.userinterface;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface UIPanel {
    /**
     *
     * @return the size of the panel
     */
    int size();

    /**
     *
     * @return whether the panel is build to behave like is has a form
     */
    boolean hasForm();

    /**
     *
     * @param index the <b>local</b> index of the element
     * @return the element at the given index
     */
    UIElementResult getUIElement(int index);
}
