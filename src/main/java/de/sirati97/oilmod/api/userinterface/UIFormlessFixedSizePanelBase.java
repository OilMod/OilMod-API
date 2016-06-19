package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSFixedSizePanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSPanel;
import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIFormlessFixedSizePanelBase extends UIFormlessPanel{
    private final NMSFixedSizePanel nmsPanel;

    public UIFormlessFixedSizePanelBase(int size) {
        super(size);
        nmsPanel = UIHelperBase.getInstance().createFixedSizePanel(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UIElementResult getUIElement(int index) {
        return nmsPanel.getElement(index);
    }

    NMSPanel _getNmsPanel() {
        return nmsPanel;
    }


    public void setUIElement(int index, UIElement element) {
        nmsPanel.setElement(index, element);
    }
}
