package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.UIElement;
import de.sirati97.oilmod.api.userinterface.internal.NMSUIElement;

/**
 * Created by sirati97 on 15.06.2016 for OilMod-Api.
 */
public interface UIElementResult {
    int getIndex();
    NMSUIElement getNMSUIElement();
    UIElement getUIElement();
}
