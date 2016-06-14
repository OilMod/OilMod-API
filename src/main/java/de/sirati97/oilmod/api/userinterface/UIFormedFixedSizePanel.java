package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSBasedUIPanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSFixedSizePanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSPanel;
import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public class UIFormedFixedSizePanel extends UIFormedPanel implements NMSBasedUIPanel{
    private final NMSFixedSizePanel nmsPanel;

    public UIFormedFixedSizePanel(int width, int height) {
        super(width, height);
        nmsPanel = UIHelperBase.getInstance().createFixedSizePanel(this);
    }

    @Override
    protected UIElement getUIElement(int left, int top, int rawIndex) {
        return nmsPanel.getElement(rawIndex);
    }

    @Override
    public NMSPanel getNmsPanel() {
        return nmsPanel;
    }


    protected void setUIElement(int index, UIElement element) {
        nmsPanel.setElement(index, element);
    }

    protected void setUIElement(int left, int top, UIElement element) {
        setUIElement(toIndex(left, top), element);
    }
}
