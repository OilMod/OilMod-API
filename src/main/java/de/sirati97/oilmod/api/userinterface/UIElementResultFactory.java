package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;

/**
 * Created by sirati97 on 15.06.2016 for OilMod-Api.
 */
public final class UIElementResultFactory {
    private UIElementResultFactory() {throw new UnsupportedOperationException();}


    /*Will return null if element is null*/
    public static UIElementResult createResult(UIElement element, int index) {
        return UIHelperBase.getInstance().createUIElementResult(element, index);
    }
}
