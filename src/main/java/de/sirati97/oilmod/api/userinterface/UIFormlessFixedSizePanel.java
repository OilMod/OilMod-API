package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSBasedUIPanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSPanel;

/**
 * Created by sirati97 on 18.06.2016 for OilMod-Api.
 */
public final class UIFormlessFixedSizePanel extends UIFormlessFixedSizePanelBase implements NMSBasedUIPanel {
    public UIFormlessFixedSizePanel(int size) {
        super(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NMSPanel getNmsPanel() {
        return _getNmsPanel();
    }
}
