package org.oilmod.api.inventory;

import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.userinterface.IInteractableUIElement;

/**
 * Created by sirati97 on 12.02.2016.
 */
public abstract class ModInventoryObjectBase<T extends ModInventoryObjectBase> {
    private final InventoryData<T> serializableDataHandler;

    public ModInventoryObjectBase(InventoryData<T> serializableDataHandler) {
        this.serializableDataHandler = serializableDataHandler;
        serializableDataHandler.setOilApiInv((T) this);

    }

    public InventoryData<T> getSerializableDataHandler() {
        return serializableDataHandler;
    }

    public InventoryRep getBukkitInventory() {
        return getSerializableDataHandler().getData().getBukkitInventory();
    }

    public Object getNMSInventory() {
        return getSerializableDataHandler().getData().getNMSInventory();
    }

    public String getNBTName() {
        return getSerializableDataHandler().getName();
    }

    public IInteractableUIElement createUIElement() {
        return getSerializableDataHandler().getData().createUIElement();
    }

    public void setTitle(String title) {getSerializableDataHandler().getData().setTitle(title);}

}
