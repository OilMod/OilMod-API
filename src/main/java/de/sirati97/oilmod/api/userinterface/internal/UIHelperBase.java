package de.sirati97.oilmod.api.userinterface.internal;

import de.sirati97.oilmod.api.userinterface.Click;
import de.sirati97.oilmod.api.userinterface.IInteractableUIElement;
import de.sirati97.oilmod.api.userinterface.IUIElement;
import de.sirati97.oilmod.api.userinterface.UIElementResult;
import de.sirati97.oilmod.api.userinterface.UIMultiPanel;
import de.sirati97.oilmod.api.userinterface.UIPanel;
import org.bukkit.entity.Player;

/**
 * Created by sirati97 on 15.01.2016.
 */
public abstract class UIHelperBase {
    private static UIHelperBase instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(UIHelperBase instance) {
        if (UIHelperBase.instance == null) {
            synchronized (MUTEX) {
                if (UIHelperBase.instance == null) {
                    UIHelperBase.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static UIHelperBase getInstance() {
        return instance;
    }

    public abstract NMSMultiPanel createMultiPanel(UIMultiPanel oilPanel);

    public abstract NMSFixedSizePanel createFixedSizePanel(UIPanel oilPanel);

    public abstract UserInterfaceFactory getInterfaceFactory();

    public abstract UIElementResult createUIElementResult(IUIElement element, int index);

    public abstract NMSClickData createNMSClickData(UserInterface userInterface);

    public abstract void handleInventoryClick(IInteractableUIElement element, Player player, int index, Click click, NMSClickData data);
}
