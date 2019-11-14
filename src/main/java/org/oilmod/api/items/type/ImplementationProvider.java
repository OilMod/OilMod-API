package org.oilmod.api.items.type;

import gnu.trove.set.hash.THashSet;
import org.oilmod.api.items.NMSItem;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.rep.item.ItemRep;

import java.util.Collections;
import java.util.Set;

public abstract class ImplementationProvider {
    //Static members
    public static final ImplementationProvider PICKAXE;
    public static final ImplementationProvider AXE;
    public static final ImplementationProvider SHOVEL;
    public static final ImplementationProvider SHEARS;
    public static final ImplementationProvider HOE;
    public static final ImplementationProvider TOOL_CUSTOM;
    public static final ImplementationProvider SWORD;
    public static final ImplementationProvider MELEE_WEAPON_CUSTOM;
    public static final ImplementationProvider BOW;
    public static final ImplementationProvider BOW_CUSTOM;
    public static final ImplementationProvider FOOD;
    public static final ImplementationProvider ARMOR_HELMET;
    public static final ImplementationProvider ARMOR_CHESTPLATE;
    public static final ImplementationProvider ARMOR_LEGGINGS;
    public static final ImplementationProvider ARMOR_SHOES;
    public static final ImplementationProvider ARMOR_CUSTOM;
    public static final ImplementationProvider CUSTOM;

    private final static Set<ImplementationProvider> registeredSet = new THashSet<>();
    private final static Set<ImplementationProvider> registeredSetRead = Collections.unmodifiableSet(registeredSet);

    //Enum
    public enum TypeEnum {
        PICKAXE,
        AXE,
        SHOVEL,
        SHEARS,
        HOE,
        TOOL_CUSTOM,
        SWORD,
        MELEE_WEAPON_CUSTOM,
        BOW,
        BOW_CUSTOM,
        FOOD,
        ARMOR_HELMET,
        ARMOR_CHESTPLATE,
        ARMOR_LEGGINGS,
        ARMOR_SHOES,
        ARMOR_CUSTOM,
        CUSTOM
    }

    //Backing implementation
    public static abstract class IPHelper {
        private static IPHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(IPHelper instance) {
            if (IPHelper.instance == null) {
                synchronized (MUTEX) {
                    if (IPHelper.instance == null) {
                        IPHelper.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static IPHelper getInstance() {
            return instance;
        }
        protected abstract void apiInit(); //prepare stuff
        protected abstract void apiPostInit(); //dunno for this one
        protected abstract ImplementationProvider getProvider(TypeEnum itemType);
        protected void unregister(ImplementationProvider itemType) {
            registeredSet.remove(itemType);
        }
    }

    //static methods
    static {
        IPHelper h = IPHelper.getInstance();
        h.apiInit();
        PICKAXE = h.getProvider(TypeEnum.PICKAXE);
        AXE = h.getProvider(TypeEnum.AXE);
        SHOVEL = h.getProvider(TypeEnum.SHOVEL);
        SHEARS = h.getProvider(TypeEnum.SHEARS);
        HOE = h.getProvider(TypeEnum.HOE);
        TOOL_CUSTOM = h.getProvider(TypeEnum.TOOL_CUSTOM);
        SWORD = h.getProvider(TypeEnum.SWORD);
        MELEE_WEAPON_CUSTOM = h.getProvider(TypeEnum.MELEE_WEAPON_CUSTOM);
        BOW = h.getProvider(TypeEnum.BOW);
        BOW_CUSTOM = h.getProvider(TypeEnum.BOW_CUSTOM);
        FOOD = h.getProvider(TypeEnum.FOOD);
        ARMOR_HELMET = h.getProvider(TypeEnum.ARMOR_HELMET);
        ARMOR_CHESTPLATE = h.getProvider(TypeEnum.ARMOR_CHESTPLATE);
        ARMOR_LEGGINGS = h.getProvider(TypeEnum.ARMOR_LEGGINGS);
        ARMOR_SHOES = h.getProvider(TypeEnum.ARMOR_SHOES);
        ARMOR_CUSTOM = h.getProvider(TypeEnum.ARMOR_CUSTOM);
        CUSTOM = h.getProvider(TypeEnum.CUSTOM);
        h.apiPostInit();
    }

    public static Set<ImplementationProvider> getAll() {
        return registeredSetRead;
    }


    //fields
    private final TypeEnum typeEnum;

    //constructor
    protected ImplementationProvider(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
        registeredSet.add(this);
    }

    //methods
    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    //abstract methods
    public abstract ItemRep implement(OilItem item);
}
