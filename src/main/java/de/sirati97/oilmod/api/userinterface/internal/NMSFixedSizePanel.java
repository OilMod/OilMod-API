package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.UIElement;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface NMSFixedSizePanel extends NMSPanel {
    UIElement getElement(int index);
    void setElement(int index, UIElement element);
}
