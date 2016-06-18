package de.sirati97.oilmod.api.userinterface;

import de.sirati97.oilmod.api.userinterface.internal.NMSFixedSizePanel;
import de.sirati97.oilmod.api.userinterface.internal.NMSPanel;
import de.sirati97.oilmod.api.userinterface.internal.UIHelperBase;

/**
 * Created by sirati97 on 14.06.2016 for OilMod-Api.
 */
public abstract class UIFormedFixedSizePanelBase extends UIFormedPanel{
    private final NMSFixedSizePanel nmsPanel;

    public UIFormedFixedSizePanelBase(int width, int height) {
        super(width, height);
        nmsPanel = UIHelperBase.getInstance().createFixedSizePanel(this);
    }

    @Override
    protected UIElementResult getUIElement(int left, int top, int rawIndex) {
        return nmsPanel.getElement(rawIndex);
    }

    NMSPanel _getNmsPanel() {
        return nmsPanel;
    }
    
    public void setUIElement(int index, UIElement element) {
        nmsPanel.setElement(index, element);
    }

    public void setUIElement(int left, int top, UIElement element) {
        setUIElement(toIndex(left, top), element);
    }
}
