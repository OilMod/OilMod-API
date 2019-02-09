package org.oilmod.api.userinterface.internal;

import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.userinterface.Click;
import org.oilmod.api.userinterface.IInteractableUIElement;
import org.oilmod.api.userinterface.IUIElement;
import org.oilmod.api.userinterface.UIElementResult;
import org.oilmod.api.userinterface.UIMultiPanel;
import org.oilmod.api.userinterface.UIPanel;

/**
 * Created by sirati97 on 15.01.2016.
 */
public abstract class UIHelper {
    private static UIHelper instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(UIHelper instance) {
        if (UIHelper.instance == null) {
            synchronized (MUTEX) {
                if (UIHelper.instance == null) {
                    UIHelper.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static UIHelper getInstance() {
        return instance;
    }

    public abstract NMSMultiPanel createMultiPanel(UIMultiPanel oilPanel);

    public abstract NMSFixedSizePanel createFixedSizePanel(UIPanel oilPanel);

    public abstract UserInterfaceFactory getInterfaceFactory();

    public abstract UIElementResult createUIElementResult(IUIElement element, int index);

    public abstract NMSClickData createNMSClickData(UserInterface userInterface);

    public abstract void handleInventoryClick(IInteractableUIElement element, EntityHumanRep player, int index, Click click, NMSClickData data);
}
