package org.oilmod.api.userinterface;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-Api.
 */
public class UIEmptyPanel extends UIFormlessPanel {
    public UIEmptyPanel(int size) {
        super(size);
    }

    @Override
    public UIElementResult getUIElement(int index) {
        return null;
    }
}
