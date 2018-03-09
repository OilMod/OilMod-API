package org.oilmod.api.items;

import gnu.trove.set.hash.THashSet;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.nms.NMSBlockType;
import org.oilmod.api.util.OilKey;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

public abstract class ItemType {
    //Static members
    public static final ItemType GENERIC;
    public static final ItemType PICKAXE;
    public static final ItemType AXE;
    public static final ItemType SHOVEL;
    public static final ItemType SHEARS;
    public static final ItemType SWORD;
    public static final ItemType HOE;
    public static final ItemType FOOD;
    public static final ItemType THROWABLE;
    public static final ItemType ARMOR;

    private final static EnumMap<ItemTypeEnum, ItemType> enumMap = new EnumMap<>(ItemTypeEnum.class);
    private final static Set<ItemType> registeredSet = new THashSet<>();
    private final static Set<ItemType> registeredSetRead = Collections.unmodifiableSet(registeredSet);

    //Enum
    public enum ItemTypeEnum {
        GENERIC,
        PICKAXE,
        AXE,
        SHOVEL,
        SHEARS,
        SWORD,
        HOE,
        FOOD,
        THROWABLE,
        ARMOR;
    }

    //Backing implementation
    public static abstract class ItemTypeHelper {
        private static ItemTypeHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(ItemTypeHelper instance) {
            if (ItemTypeHelper.instance == null) {
                synchronized (MUTEX) {
                    if (ItemTypeHelper.instance == null) {
                        ItemTypeHelper.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static ItemTypeHelper getInstance() {
            return instance;
        }
        protected abstract void apiInit(); //prepare stuff
        protected abstract void apiPostInit(); //dunno for this one
        protected abstract ItemType getVanillaItemType(ItemTypeEnum blockType);
        protected void unregister(ItemType type) {
            registeredSet.remove(type);
        }
    }

    //static methods
    static {
        ItemTypeHelper h = ItemTypeHelper.getInstance();
        h.apiInit();
        GENERIC = h.getVanillaItemType(ItemTypeEnum.GENERIC);
        PICKAXE = h.getVanillaItemType(ItemTypeEnum.PICKAXE);
        AXE = h.getVanillaItemType(ItemTypeEnum.AXE);
        SHOVEL = h.getVanillaItemType(ItemTypeEnum.SHOVEL);
        SHEARS = h.getVanillaItemType(ItemTypeEnum.SHEARS);
        SWORD = h.getVanillaItemType(ItemTypeEnum.SWORD);
        HOE = h.getVanillaItemType(ItemTypeEnum.HOE);
        FOOD = h.getVanillaItemType(ItemTypeEnum.FOOD);
        THROWABLE = h.getVanillaItemType(ItemTypeEnum.THROWABLE);
        ARMOR = h.getVanillaItemType(ItemTypeEnum.ARMOR);
        h.apiPostInit();
    }

    public static Set<ItemType> getAll() {
        return registeredSetRead;
    }

    public static ItemType getStandard(ItemTypeEnum type) {
        return enumMap.get(type);
    }


    //fields
    private final ItemTypeEnum blockTypeEnum;

    //constructor

    protected ItemType(ItemTypeEnum blockTypeEnum) {
        this.blockTypeEnum = blockTypeEnum;
        enumMap.put(blockTypeEnum, this);
        registeredSet.add(this);
    }

    //methods


    public ItemTypeEnum getBlockTypeEnum() {
        return blockTypeEnum;
    }

    public abstract boolean canDestroySpecialBlock(OilItem<?> item, BlockState blockState, BlockType blockType);
    public abstract float getDestroySpeed(OilItem<?> item, BlockState blockState, BlockType blockType);
    public abstract int getMaxStackSize();

}
