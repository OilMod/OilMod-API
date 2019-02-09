package org.oilmod.api.items;

import gnu.trove.set.hash.THashSet;
import org.apache.commons.lang3.Validate;
import org.oilmod.api.items.nms.NMSEnchantmentType;
import org.oilmod.api.util.IKeyed;
import org.oilmod.api.util.OilKey;
import org.oilmod.api.util.OilRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

public abstract class EnchantmentType implements IKeyed {
    //Static members
    public static final EnchantmentType ALL_VANILLA;
    public static final EnchantmentType ALL;
    public static final EnchantmentType ARMOR;
    public static final EnchantmentType ARMOR_BOOTS;
    public static final EnchantmentType ARMOR_LEGGINGS;
    public static final EnchantmentType ARMOR_CHEST;
    public static final EnchantmentType ARMOR_HELMET;
    public static final EnchantmentType WEAPON;
    public static final EnchantmentType DIGGER;
    public static final EnchantmentType FISHING_ROD;
    /** Special - usually not needed -  is durable*/
    public static final EnchantmentType BREAKABLE;
    public static final EnchantmentType BOW;
    /** Special - usually not needed - eligibility to curses */
    public static final EnchantmentType WEARABLE;

    /** Special - marks lack of special enchantability, cannot be used in custom enchantments */
    public static final EnchantmentType NONE;

    private final static EnumMap<EnchantmentType.EnchantmentTypeEnum, EnchantmentType> enumMap = new EnumMap<>(EnchantmentType.EnchantmentTypeEnum.class);


    private final static OilRegistry<EnchantmentType> registry = new OilRegistry<>();
    public static final String CIRCLE_DEPENDENCIES = "Cannot create circle dependencies";

    //Enum
    public enum EnchantmentTypeEnum {
        ALL_VANILLA, ALL, ARMOR , ARMOR_BOOTS, ARMOR_LEGGINGS, ARMOR_CHEST , ARMOR_HELMET, WEAPON , DIGGER , FISHING_ROD , BREAKABLE , BOW , WEARABLE ,NONE, ENUM_MISSING, CUSTOM;
    }

    //Backing implementation
    public static abstract class EnchantmentTypeHelper {
        private static EnchantmentType.EnchantmentTypeHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(EnchantmentType.EnchantmentTypeHelper instance) {
            if (EnchantmentType.EnchantmentTypeHelper.instance == null) {
                synchronized (MUTEX) {
                    if (EnchantmentType.EnchantmentTypeHelper.instance == null) {
                        EnchantmentType.EnchantmentTypeHelper.instance = instance;
                        EnchantmentType.init();
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static EnchantmentType.EnchantmentTypeHelper getInstance() {
            return instance;
        }
        protected abstract void apiInit(); //prepare stuff
        protected abstract void apiPostInit(); //register missing and so on
        protected abstract EnchantmentType getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum blockType);
        protected abstract NMSEnchantmentType registerCustom(EnchantmentType blockType);
        protected void unregister(OilKey key) {
            registry.unregister(key);
        }
    }

    //static methods
    static {
        EnchantmentType.EnchantmentTypeHelper h = EnchantmentType.EnchantmentTypeHelper.getInstance();
        h.apiInit();
        ALL_VANILLA = h.getVanillaEnchantmentType(EnchantmentTypeEnum.ALL_VANILLA);
        ALL = h.getVanillaEnchantmentType(EnchantmentTypeEnum.ALL);
        ARMOR = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.ARMOR);
        ARMOR_BOOTS = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.ARMOR_BOOTS);
        ARMOR_LEGGINGS = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.ARMOR_LEGGINGS);
        ARMOR_CHEST = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.ARMOR_CHEST);
        ARMOR_HELMET = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.ARMOR_HELMET);
        WEAPON = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.WEAPON);
        DIGGER = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.DIGGER);
        FISHING_ROD = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.FISHING_ROD);
        BREAKABLE = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.BREAKABLE);
        BOW = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.BOW);
        WEARABLE = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.WEARABLE);
        NONE = h.getVanillaEnchantmentType(EnchantmentType.EnchantmentTypeEnum.NONE);

        h.apiPostInit();
    }

    private static void init() {}

    public static EnchantmentType getStandard(EnchantmentType.EnchantmentTypeEnum type) {
        return enumMap.get(type);
    }


    public static EnchantmentType getByKey(OilKey key) {
        return registry.get(key);
    }

    public static Set<EnchantmentType> getAll() {
        return registry.getRegistered();
    }

    //fields
    private final NMSEnchantmentType nmsEnchantmentType;
    private final OilKey key;
    private final EnchantmentType.EnchantmentTypeEnum blockTypeEnum;
    private final Set<EnchantmentType> subtypes;
    private final Set<EnchantmentType> subtypesReadOnly;

    //constructor

    protected EnchantmentType(NMSEnchantmentType nmsEnchantmentType, OilKey key, EnchantmentType.EnchantmentTypeEnum blockTypeEnum, EnchantmentType... subtypes) {

        this.subtypes = new THashSet<>(Arrays.asList(subtypes));
        this.subtypesReadOnly = Collections.unmodifiableSet(this.subtypes);
        validateSubtypes();
        this.nmsEnchantmentType = nmsEnchantmentType;
        this.key = key;
        this.blockTypeEnum = blockTypeEnum;
        if (blockTypeEnum != EnchantmentType.EnchantmentTypeEnum.CUSTOM && blockTypeEnum != EnchantmentType.EnchantmentTypeEnum.ENUM_MISSING) {
            enumMap.put(blockTypeEnum, this);
        }
        registry.register(this);
    }

    private EnchantmentType(OilKey key, EnchantmentType... subtypes) {

        this.subtypes = new THashSet<>(Arrays.asList(subtypes));
        this.subtypesReadOnly = Collections.unmodifiableSet(this.subtypes);
        validateSubtypes();
        this.key = key;
        this.blockTypeEnum = EnchantmentType.EnchantmentTypeEnum.CUSTOM;
        this.nmsEnchantmentType = EnchantmentType.EnchantmentTypeHelper.getInstance().registerCustom(this);
        registry.register(this);
    }

    //methods
    public EnchantmentType.EnchantmentTypeEnum getEnchantmentTypeEnum() {
        return blockTypeEnum;
    }

    public NMSEnchantmentType getNmsEnchantmentType() {
        return nmsEnchantmentType;
    }

    public OilKey getOilKey() {
        return key;
    }

    public boolean canEnchant(NMSItem item) {
        for (EnchantmentType enchantmentType:subtypes) {
            if (enchantmentType.canEnchant(item)) return true;
        }
        return nmsEnchantmentType.canEnchantNMS(item);
    }

    public Set<EnchantmentType> getSubtypes() {
        return subtypesReadOnly;
    }

    private void validateSubtypes() {
        Validate.isTrue(!subtypes.contains(null));
    }

    public void addSubtype(EnchantmentType enchantmentType) {
        Validate.isTrue(!enchantmentType.containsSubtype(this), CIRCLE_DEPENDENCIES);
        Validate.notNull(enchantmentType);
        Validate.isTrue(enchantmentType != this, CIRCLE_DEPENDENCIES);
        subtypes.add(enchantmentType);
    }

    public boolean containsSubtype(EnchantmentType enchantmentType) {
        if (enchantmentType == this)return true;
        if (subtypes.contains(enchantmentType))return true;
        for (EnchantmentType subtype:subtypes) {
            if (subtype.containsSubtype(enchantmentType))return true;
        }
        return false;
    }

    public boolean containsSubtypeDirectly(EnchantmentType enchantmentType) {
        return subtypes.contains(enchantmentType);
    }

    public void removeSubtype(EnchantmentType enchantmentType) {
        Validate.isTrue(!containsSubtype(enchantmentType) || subtypes.contains(enchantmentType), "Cannot remove; subtype is only subtype of subtype");
        subtypes.remove(enchantmentType);
    }

    //Custom EnchantmentType
    public static class CustomEnchantmentType extends EnchantmentType {

        public CustomEnchantmentType(OilKey key, EnchantmentType... subtypes) {
            super(key, subtypes);
        }

    }


    

}
