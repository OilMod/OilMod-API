package org.oilmod.api.userinterface;

import org.oilmod.api.userinterface.internal.NMSBasedUIPanel;
import org.oilmod.api.userinterface.internal.NMSMultiPanel;
import org.oilmod.api.userinterface.internal.NMSPanel;
import org.oilmod.api.userinterface.internal.UIHelper;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public final class UIMultiPanel extends UIFormlessPanel implements NMSBasedUIPanel {
    private final NMSMultiPanel nmsMultiPanel;

    public UIMultiPanel(int size) {
        super(size);
        nmsMultiPanel = UIHelper.getInstance().createMultiPanel(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UIElementResult getUIElement(int index) {
        return nmsMultiPanel.getUIElement(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NMSPanel getNmsPanel() {
        return nmsMultiPanel;
    }
}
