package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSBasedUIPanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSPanel;

/**
 * Created by sirati97 on 18.06.2016 for OilMod-Api.
 */
public final class UIFormedFixedSizePanel extends UIFormedFixedSizePanelBase implements NMSBasedUIPanel {
    public UIFormedFixedSizePanel(int width, int height) {
        super(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NMSPanel getNmsPanel() {
        return _getNmsPanel();
    }
}
