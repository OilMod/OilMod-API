package org.oilmod.api.userinterface;

import org.oilmod.api.inventory.ModInventoryObjectBase;

/**
 * Created by sirati97 on 27.06.2016 for OilMod-Api.
 */
public class UIOilInventoryPanel extends UIFormlessPanel {
    private final IInteractableUIElement element;

    public UIOilInventoryPanel(ModInventoryObjectBase inventory) {
        super(inventory.getBukkitInventory().getTotalSize());
        this.element = inventory.createUIElement();
    }

    @Override
    public UIElementResult getUIElement(int index) {
        return UIElementResultFactory.createResult(element, index);
    }
}
