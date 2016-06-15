package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSBasedUIPanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSMultiPanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSPanel;
import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public class UIMultiPanel extends UIFormlessPanel implements NMSBasedUIPanel {
    private final NMSMultiPanel nmsMultiPanel;

    public UIMultiPanel(int size) {
        super(size);
        nmsMultiPanel = UIHelperBase.getInstance().createMultiPanel(this);
    }

    @Override
    public UIElementResult getUIElement(int index) {
        return nmsMultiPanel.getUIElement(index);
    }


    @Override
    public NMSPanel getNmsPanel() {
        return nmsMultiPanel;
    }
}
