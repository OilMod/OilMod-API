package de.sirati97.oilmod.api.inventory;

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

    //###Basic/ChestInventory###
    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int size, String inventoryTitle) {
        return createBasicInventory(nbtName, itemStack, size, inventoryTitle, false);
    }

    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int size, String inventoryTitle, boolean mainItemstackInventory) {
        return createBasicInventory(nbtName, itemStack, size, inventoryTitle, null, mainItemstackInventory);
    }

    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int size, String inventoryTitle, ItemFilter filter) {
        return createBasicInventory(nbtName, itemStack, size, inventoryTitle, filter, false);
    }

    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int size, String inventoryTitle, ItemFilter filter, boolean mainItemstackInventory) {
        ObjectFactory<ModNMSIInventory<ModInventoryObject>> factory = getBasicInventoryFactory(itemStack, size, inventoryTitle, filter);
        InventoryData<ModInventoryObject> iData = new InventoryData<>(nbtName, itemStack, factory, false);
        ModInventoryObject result = new ModInventoryObject(iData);
        checkInventoryHolder(itemStack, result, mainItemstackInventory);
        return result;
    }

    //###FurnaceInventory###
    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle) {
        return createFurnaceInventory(nbtName, itemStack, inventoryTitle, false);
    }

    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle, boolean mainItemstackInventory) {
        return createFurnaceInventory(nbtName, itemStack, inventoryTitle, null, mainItemstackInventory);
    }

    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle, ItemFilter filter) {
        return createFurnaceInventory(nbtName, itemStack, inventoryTitle, filter, false);
    }

    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle, ItemFilter filter, boolean mainItemstackInventory) {
        return createSpecialFurnaceInventory(new InventoryObjectFactory<ModFurnaceInventoryObject>() {
            @Override
            public ModFurnaceInventoryObject create(InventoryData<ModFurnaceInventoryObject> iData) {
                return new ModFurnaceInventoryObject(iData);
            }
        },nbtName,itemStack,inventoryTitle,filter,mainItemstackInventory);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle) {
        return createSpecialFurnaceInventory(inventoryObjectFactory,nbtName, itemStack, inventoryTitle, false);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle, boolean mainItemstackInventory) {
        return createSpecialFurnaceInventory(inventoryObjectFactory,nbtName, itemStack, inventoryTitle, null, mainItemstackInventory);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle, ItemFilter filter) {
        return createSpecialFurnaceInventory(inventoryObjectFactory,nbtName, itemStack, inventoryTitle, filter, false);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle, ItemFilter filter, boolean mainItemstackInventory) {
        ObjectFactory<ModNMSIInventory<ModFurnaceInventoryObject>> factory = getFurnaceInventoryFactory(itemStack, inventoryTitle, filter);
        InventoryData<ModFurnaceInventoryObject> iData = new InventoryData<>(nbtName, itemStack, factory, false);
        ModFurnaceInventoryObject result = inventoryObjectFactory.create(iData);
        checkInventoryHolder(itemStack, result, mainItemstackInventory);
        return result;
    }

    //###Other stuff###
    protected void checkInventoryHolder(OilItemStack itemStack, ModInventoryObjectBase inventory, boolean mainItemstackInventory) {
        if (mainItemstackInventory || itemStack.getInventory() == null) {
            itemStack.setMainInventory(inventory);
        }
    }

    //###Factories###
    protected abstract ObjectFactory<ModNMSIInventory<ModInventoryObject>> getBasicInventoryFactory(OilItemStack itemStack, int size, String inventoryTitle, ItemFilter filter);
    protected abstract ObjectFactory<ModNMSIInventory<ModFurnaceInventoryObject>> getFurnaceInventoryFactory(OilItemStack itemStack, String inventoryTitle, ItemFilter filter);

}
