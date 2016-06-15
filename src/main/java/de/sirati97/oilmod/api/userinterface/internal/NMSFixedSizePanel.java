package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.UIElement;
import de.sirati97.oilmod.api.userinterface.UIElementResult;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface NMSFixedSizePanel extends NMSPanel {
    UIElementResult getElement(int index);
    void setElement(int index, UIElement element);
}
