package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSBasedUIPanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSFixedSizePanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSPanel;
import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public class UIFormlessFixedSizePanel extends UIFormlessPanel implements NMSBasedUIPanel{
    private final NMSFixedSizePanel nmsPanel;

    public UIFormlessFixedSizePanel(int size) {
        super(size);
        nmsPanel = UIHelperBase.getInstance().createFixedSizePanel(this);
    }

    @Override
    public UIElementResult getUIElement(int index) {
        return nmsPanel.getElement(index);
    }

    @Override
    public NMSPanel getNmsPanel() {
        return nmsPanel;
    }


    public void setUIElement(int index, UIElement element) {
        nmsPanel.setElement(index, element);
    }
}
