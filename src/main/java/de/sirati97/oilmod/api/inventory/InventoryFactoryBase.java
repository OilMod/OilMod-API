package de.sirati97.oilmod.api.inventory;

import de.sirati97.oilmod.api.data.CompoundSerializableData;
import de.sirati97.oilmod.api.data.ObjectFactory;
import de.sirati97.oilmod.api.items.OilItemStack;

/**
 * Created by sirati97 on 15.01.2016.
 */
public abstract class InventoryFactoryBase {
    private static InventoryFactoryBase instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(InventoryFactoryBase instance) {
        if (InventoryFactoryBase.instance == null) {
            synchronized (MUTEX) {
                if (InventoryFactoryBase.instance == null) {
                    InventoryFactoryBase.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static InventoryFactoryBase getInstance() {
        return instance;
    }


    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int size, String inventoryTitle) {
        return createBasicInventory(nbtName, itemStack, size, inventoryTitle, false);
    }


    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int size, String inventoryTitle, boolean mainItemstackInventory) {
        ObjectFactory<ModNMSIInventory> factory = getBasicInventoryFactory(itemStack, size, inventoryTitle);
        CompoundSerializableData<ModNMSIInventory> iData = new CompoundSerializableData<>(nbtName, itemStack, factory, false);
        ModInventoryObject result = new ModInventoryObject(iData);
        checkInventoryHolder(itemStack, result, mainItemstackInventory);
        return result;
    }

    protected void checkInventoryHolder(OilItemStack itemStack, ModInventoryObject inventory, boolean mainItemstackInventory) {
        if (mainItemstackInventory || itemStack.getInventory() == null) {
            itemStack.setMainInventory(inventory);
        }
    }

    protected abstract ObjectFactory<ModNMSIInventory> getBasicInventoryFactory(OilItemStack itemStack, int size, String inventoryTitle);
}
