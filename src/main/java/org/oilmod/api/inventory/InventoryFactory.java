package org.oilmod.api.inventory;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.oilmod.api.crafting.ICraftingProcessor;
import org.oilmod.api.data.IDataParent;
import org.oilmod.api.data.ItemStackData;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.util.ITicker;

import java.util.List;
import java.util.function.Function;

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
    
    public static  <T extends ModInventoryObjectBase<T>> Builder<T> builder(String nbtName) {
        return new Builder<>(nbtName);
    }

    //tho only reason why this is generic is so that the create method can have output the right type without casting
    public static class Builder<T extends ModInventoryObjectBase<T>>{
        private final String nbtName;
        private int rows = -1;
        private int columns = -1;
        private ItemFilter filter;
        private boolean mainInventory;
        private Function<OilItemStack, ModNMSIInventory<T>> factory;
        private Function<InventoryData<T>, T> ctor;
        private String standardTitle;
        private ITicker ticker;
        private List<Function<InventoryRep, ICraftingProcessor>> craftingProcessors;


        public Builder(String nbtName) {
            this.nbtName = nbtName;
        }

        public Builder<T> size(int rows, int columns) {
            this.columns = columns;
            this.rows = rows;
            return checkAccess();
        }

        public Builder<T> size(int size) {
            this.columns = size;
            this.rows = 0;
            return checkAccess();
        }


        public Builder<T> chestSize(int rows) {
            this.columns = 9;
            this.rows = rows;
            return checkAccess();
        }

        public Builder<T> filter(ItemFilter filter) {
            this.filter = filter;
            return checkAccess();
        }

        public Builder<T> mainInventory() {
            this.mainInventory = true;
            return checkAccess();
        }

        public Builder<T> standardTitle(String standardTitle) {
            this.standardTitle = standardTitle;
            return checkAccess();
        }

        public  Builder<T> ticker(ITicker ticker) {
            this.ticker = ticker;
            return checkAccess();
        }


        /*public Builder<T> processors(Function<InventoryRep, ICraftingProcessor>... processors) {
            if (craftingProcessors == null) craftingProcessors = new ObjectArrayList<>(processors);
            else craftingProcessors.addAll(Arrays.asList(processors));
            return checkAccess();
        }*/

        public Builder<T> processors(Function<InventoryRep, ICraftingProcessor> processor) {
            if (craftingProcessors == null) craftingProcessors = new ObjectArrayList<>();
            craftingProcessors.add(processor);
            return checkAccess();
        }


        private Builder<T> checkAccess() {
            if (factory != null)throw new IllegalStateException("calling type setter makes builder immutable. e.g.: basic(), furnace(), crafting()");
            return this;
        }
        
        public Builder<ModInventoryObject> basic() {
            if (rows == -1 || columns == -1)throw new IllegalArgumentException("size not set!");

            Builder<ModInventoryObject> result  = (Builder<ModInventoryObject>) this; //abusing type erasure
            result.factory = getInstance().getBasicInventoryFactory(rows, columns, standardTitle, filter, getCraftingProcessors());
            result.ctor = ModInventoryObject::new;
            return result;
        }


        public Builder<ModFurnaceInventoryObject> furnace() {
            return furnaceSpecial(ModFurnaceInventoryObject::new);
        }

        public Builder<ModFurnaceInventoryObject> furnaceSpecial(Function<InventoryData<ModFurnaceInventoryObject>, ModFurnaceInventoryObject> specialFactory) {
            if (ticker == null)throw new IllegalArgumentException("ticker not set!");

            Builder<ModFurnaceInventoryObject> result  = (Builder<ModFurnaceInventoryObject>) this; //abusing type erasure
            result.factory = getInstance().getFurnaceInventoryFactory(standardTitle, ticker, filter, getCraftingProcessors());
            result.ctor = specialFactory;
            return result;
        }

        public Builder<ModPortableCraftingInventoryObject> crafting() {
            if (rows < 1 || columns < 1)throw new IllegalArgumentException("2d size not set!");

            Builder<ModPortableCraftingInventoryObject> result  = (Builder<ModPortableCraftingInventoryObject>) this; //abusing type erasure
            result.factory = getInstance().getPortableCraftingInventoryFactory(rows, columns, standardTitle, filter, getCraftingProcessors());
            result.ctor = ModPortableCraftingInventoryObject::new;
            return result;
        }


        public T create(OilItemStack itemStack) {
            if (factory == null)throw new IllegalStateException("you need to call a type setter method. e.g.: basic(), furnace(), crafting()");

            InventoryData<T> iData = new InventoryData<>(nbtName, itemStack, () -> factory.apply(itemStack), false);
            T result = ctor.apply(iData);
            getInstance().checkInventoryHolder(itemStack, result, mainInventory);
            return result;
        }
        private final static ICraftingProcessor[] emptyArrayPro = new ICraftingProcessor[0];
        private Function<InventoryRep, ICraftingProcessor[]> getCraftingProcessors() {
            return craftingProcessors==null?(inv)->emptyArrayPro:(inv)->{
                ICraftingProcessor[] result = new ICraftingProcessor[craftingProcessors.size()];
                for (int i = 0; i < result.length; i++) {
                    result[i] = craftingProcessors.get(i).apply(inv);
                }
                return result;
            };
        }
    }

    //ModPortableCraftingInventoryObject

    //###ItemStackData###
    public abstract ItemStackData createItemStackData(String name, IDataParent dataParent);

    //###Other stuff###
    protected <T extends ModInventoryObjectBase<T>> void checkInventoryHolder(OilItemStack itemStack, T inventory, boolean mainItemstackInventory) {
        if (mainItemstackInventory || itemStack.getInventory() == null) {
            itemStack.setMainInventory(inventory);
        }
    }

    //###Factories###
    protected abstract Function<OilItemStack, ModNMSIInventory<ModInventoryObject>> getBasicInventoryFactory(int rows, int columns, String inventoryTitle, ItemFilter filter, Function<InventoryRep, ICraftingProcessor[]> processorFactory);
    protected abstract Function<OilItemStack, ModNMSIInventory<ModFurnaceInventoryObject>> getFurnaceInventoryFactory(String inventoryTitle, ITicker ticker, ItemFilter filter, Function<InventoryRep, ICraftingProcessor[]> processorFactory);
    protected abstract Function<OilItemStack, ModNMSIInventory<ModPortableCraftingInventoryObject>> getPortableCraftingInventoryFactory(int width, int height, String inventoryTitle, ItemFilter filter, Function<InventoryRep, ICraftingProcessor[]> processorFactory);

}
