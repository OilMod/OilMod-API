package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.UIPanel;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public interface NMSMultiPanel extends NMSPanel {
    void setPanel(int index, UIPanel panel, int offset, int upperBound);
    void addPanel(int index, UIPanel panel, int offset, int upperBound);
    void removeIndex(UIPanel panel);
    UIPanel getPanel(int index);
}
