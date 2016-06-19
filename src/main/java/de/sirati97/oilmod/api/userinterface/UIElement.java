package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSUIElement;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIElement implements IUIElement {
    private NMSUIElement nmsWrapper;

    /**
     * {@inheritDoc}
     */
    public void setNmsWrapper(NMSUIElement nmsWrapper) {
        this.nmsWrapper = nmsWrapper;
    }

    /**
     * {@inheritDoc}
     */
    public NMSUIElement getNmsWrapper() {
        return nmsWrapper;
    }
}
