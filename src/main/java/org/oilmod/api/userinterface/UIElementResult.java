package org.oilmod.api.userinterface;

import org.oilmod.api.userinterface.internal.NMSUIElement;

/**
 * Created by sirati97 on 15.06.2016 for OilMod-Api.
 */
public interface UIElementResult {
    /**
     *
     * @return local index of Element
     */
    int getIndex();

    /**
     *
     * @return NMSElement/Wrapper
     */
    NMSUIElement getNMSUIElement();

    /**
     *
     * @return OilElement
     */
    IUIElement getUIElement();
}
