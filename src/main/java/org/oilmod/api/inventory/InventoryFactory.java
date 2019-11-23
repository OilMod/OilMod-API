package org.oilmod.api.inventory;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.data.DataParent;
import org.oilmod.api.data.ItemStackData;
import org.oilmod.api.data.ObjectFactory;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.util.ITicker;

/**
 * Created by sirati97 on 15.01.2016.
 */
public abstract class InventoryFactory {
    private static InventoryFactory instance;
    private static final Object MUTEX = new Object();
    private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

    public static void setInstance(InventoryFactory instance) {
        if (InventoryFactory.instance == null) {
            synchronized (MUTEX) {
                if (InventoryFactory.instance == null) {
                    InventoryFactory.instance = instance;
                } else {
                    throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                }
            }
        } else {
            throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
        }
    }

    public static InventoryFactory getInstance() {
        return instance;
    }
    //todo use builders to make this less of a mess

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
        Validate.isTrue(size%9==0, "Illegal size, must be multiple of 9");
        return createBasicInventory(nbtName, itemStack, size/9, 9, inventoryTitle, filter, mainItemstackInventory);
    }


    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int rows, int columns, String inventoryTitle) {
        return createBasicInventory(nbtName, itemStack, rows, columns, inventoryTitle, false);
    }

    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int rows, int columns, String inventoryTitle, boolean mainItemstackInventory) {
        return createBasicInventory(nbtName, itemStack, rows, columns, inventoryTitle, null, mainItemstackInventory);
    }

    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int rows, int columns, String inventoryTitle, ItemFilter filter) {
        return createBasicInventory(nbtName, itemStack, rows, columns, inventoryTitle, filter, false);
    }
    
    public ModInventoryObject createBasicInventory(String nbtName, OilItemStack itemStack, int rows, int columns, String inventoryTitle, ItemFilter filter, boolean mainItemstackInventory) {
        ObjectFactory<ModNMSIInventory<ModInventoryObject>> factory = getBasicInventoryFactory(itemStack, rows, columns, inventoryTitle, filter);
        InventoryData<ModInventoryObject> iData = new InventoryData<>(nbtName, itemStack, factory, false);
        ModInventoryObject result = new ModInventoryObject(iData);
        checkInventoryHolder(itemStack, result, mainItemstackInventory);
        return result;
    }

    //###FurnaceInventory###
    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker) {
        return createFurnaceInventory(nbtName, itemStack, inventoryTitle, ticker, false);
    }

    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker, boolean mainItemstackInventory) {
        return createFurnaceInventory(nbtName, itemStack, inventoryTitle, ticker, null, mainItemstackInventory);
    }

    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker, ItemFilter filter) {
        return createFurnaceInventory(nbtName, itemStack, inventoryTitle, ticker, filter, false);
    }

    public ModFurnaceInventoryObject createFurnaceInventory(String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker, ItemFilter filter, boolean mainItemstackInventory) {
        return createSpecialFurnaceInventory(new InventoryObjectFactory<ModFurnaceInventoryObject>() {
            @Override
            public ModFurnaceInventoryObject create(InventoryData<ModFurnaceInventoryObject> iData) {
                return new ModFurnaceInventoryObject(iData);
            }
        },nbtName,itemStack,inventoryTitle, ticker,filter,mainItemstackInventory);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker) {
        return createSpecialFurnaceInventory(inventoryObjectFactory,nbtName, itemStack, inventoryTitle, ticker, false);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker, boolean mainItemstackInventory) {
        return createSpecialFurnaceInventory(inventoryObjectFactory,nbtName, itemStack, inventoryTitle, ticker, null, mainItemstackInventory);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker, ItemFilter filter) {
        return createSpecialFurnaceInventory(inventoryObjectFactory,nbtName, itemStack, inventoryTitle, ticker, filter, false);
    }

    public ModFurnaceInventoryObject createSpecialFurnaceInventory(InventoryObjectFactory<ModFurnaceInventoryObject> inventoryObjectFactory, String nbtName, OilItemStack itemStack, String inventoryTitle, ITicker ticker, ItemFilter filter, boolean mainItemstackInventory) {
        ObjectFactory<ModNMSIInventory<ModFurnaceInventoryObject>> factory = getFurnaceInventoryFactory(itemStack, inventoryTitle, ticker, filter);
        InventoryData<ModFurnaceInventoryObject> iData = new InventoryData<>(nbtName, itemStack, factory, false);
        ModFurnaceInventoryObject result = inventoryObjectFactory.create(iData);
        checkInventoryHolder(itemStack, result, mainItemstackInventory);
        return result;
    }


    //###PortableCraftingInventory###

    public ModPortableCraftingInventoryObject createPortableCraftingInventory(String nbtName, OilItemStack itemStack, int width, int height, String inventoryTitle) {
        return createPortableCraftingInventory(nbtName, itemStack, width, height, inventoryTitle, false);
    }

    public ModPortableCraftingInventoryObject createPortableCraftingInventory(String nbtName, OilItemStack itemStack, int width, int height, String inventoryTitle, boolean mainItemstackInventory) {
        return createPortableCraftingInventory(nbtName, itemStack, width, height, inventoryTitle, null, mainItemstackInventory);
    }

    public ModPortableCraftingInventoryObject createPortableCraftingInventory(String nbtName, OilItemStack itemStack, int width, int height, String inventoryTitle, ItemFilter filter) {
        return createPortableCraftingInventory(nbtName, itemStack, width, height, inventoryTitle, filter, false);
    }

    public ModPortableCraftingInventoryObject createPortableCraftingInventory(String nbtName, OilItemStack itemStack, int width, int height, String inventoryTitle, ItemFilter filter, boolean mainItemstackInventory) {
        ObjectFactory<ModNMSIInventory<ModPortableCraftingInventoryObject>> factory = getPortableCraftingInventoryFactory(itemStack, width, height, inventoryTitle, filter);
        InventoryData<ModPortableCraftingInventoryObject> iData = new InventoryData<>(nbtName, itemStack, factory, false);
        ModPortableCraftingInventoryObject result = new ModPortableCraftingInventoryObject(iData);
        checkInventoryHolder(itemStack, result, mainItemstackInventory);
        return result;
    }



    //ModPortableCraftingInventoryObject

    //###ItemStackData###
    public abstract ItemStackData createItemStackData(String name, DataParent dataParent);

    //###Other stuff###
    protected void checkInventoryHolder(OilItemStack itemStack, ModInventoryObjectBase inventory, boolean mainItemstackInventory) {
        if (mainItemstackInventory || itemStack.getInventory() == null) {
            itemStack.setMainInventory(inventory);
        }
    }

    //###Factories###
    protected abstract ObjectFactory<ModNMSIInventory<ModInventoryObject>> getBasicInventoryFactory(OilItemStack itemStack, int rows, int columns, String inventoryTitle, ItemFilter filter);
    protected abstract ObjectFactory<ModNMSIInventory<ModFurnaceInventoryObject>> getFurnaceInventoryFactory(OilItemStack itemStack, String inventoryTitle, ITicker ticker, ItemFilter filter);
    protected abstract ObjectFactory<ModNMSIInventory<ModPortableCraftingInventoryObject>> getPortableCraftingInventoryFactory(OilItemStack itemStack, int width, int height, String inventoryTitle, ItemFilter filter);

}
