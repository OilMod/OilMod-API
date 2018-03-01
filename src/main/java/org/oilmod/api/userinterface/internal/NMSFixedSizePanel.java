package org.oilmod.api.userinterface.internal;

import org.oilmod.api.userinterface.UIElement;
import org.oilmod.api.userinterface.UIElementResult;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface NMSFixedSizePanel extends NMSPanel {
    UIElementResult getElement(int index);
    void setElement(int index, UIElement element);
}
