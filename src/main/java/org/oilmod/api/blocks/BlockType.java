package org.oilmod.api.blocks;

import gnu.trove.set.hash.THashSet;
import org.oilmod.api.blocks.nms.NMSBlockType;
import org.oilmod.api.util.OilKey;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

public abstract class BlockType {
    //Static members
    public static final BlockType AIR;
    public static final BlockType GRASS;
    public static final BlockType EARTH;
    public static final BlockType WOOD;
    public static final BlockType STONE;
    public static final BlockType ORE;
    public static final BlockType HEAVY;
    public static final BlockType WATER;
    public static final BlockType LAVA;
    public static final BlockType LEAVES;
    public static final BlockType PLANT;
    public static final BlockType REPLACEABLE_PLANT;
    public static final BlockType SPONGE;
    public static final BlockType CLOTH;
    public static final BlockType FIRE;
    public static final BlockType SAND;
    public static final BlockType ORIENTABLE;
    public static final BlockType WOOL;
    public static final BlockType SHATTERABLE;
    public static final BlockType BUILDABLE_GLASS;
    public static final BlockType TNT;
    public static final BlockType CORAL;
    public static final BlockType ICE;
    public static final BlockType SNOW_LAYER;
    public static final BlockType PACKED_ICE;
    public static final BlockType SNOW_BLOCK;
    public static final BlockType CACTUS;
    public static final BlockType CLAY;
    public static final BlockType PUMPKIN;
    public static final BlockType DRAGON_EGG;
    public static final BlockType PORTAL;
    public static final BlockType CAKE;
    public static final BlockType WEB;
    public static final BlockType PISTON;
    public static final BlockType BANNER;
    public static final BlockType UNKNOWN_J; //TODO: find out what this is

    private final static EnumMap<BlockTypeEnum, BlockType> enumMap = new EnumMap<>(BlockTypeEnum.class);
    private final static Set<BlockType> registeredSet = new THashSet<>();
    private final static Set<BlockType> registeredSetRead = Collections.unmodifiableSet(registeredSet);

    //Enum
    public enum BlockTypeEnum {
        AIR,
        GRASS,
        EARTH,
        WOOD,
        STONE,
        ORE,
        HEAVY,
        WATER,
        LAVA,
        LEAVES,
        PLANT,
        REPLACEABLE_PLANT,
        SPONGE,
        CLOTH,
        FIRE,
        SAND,
        ORIENTABLE,
        WOOL,
        SHATTERABLE,
        BUILDABLE_GLASS,
        TNT,
        CORAL,
        ICE,
        SNOW_LAYER,
        PACKED_ICE,
        SNOW_BLOCK,
        CACTUS,
        CLAY,
        PUMPKIN,
        DRAGON_EGG,
        PORTAL,
        CAKE,
        WEB,
        PISTON,
        BANNER,
        UNKNOWN_J,
        ENUM_MISSING,
        CUSTOM;
    }
    
    //Backing implementation
    public static abstract class BlockTypeHelper {
        private static BlockTypeHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(BlockTypeHelper instance) {
            if (BlockTypeHelper.instance == null) {
                synchronized (MUTEX) {
                    if (BlockTypeHelper.instance == null) {
                        BlockTypeHelper.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static BlockTypeHelper getInstance() {
            return instance;
        }
        protected abstract void apiInit(); //prepare stuff
        protected abstract void apiPostInit(); //register missing and so on
        protected abstract BlockType getVanillaBlockType(BlockTypeEnum blockType);
        protected abstract NMSBlockType registerCustom(BlockType blockType);
        protected abstract BlockType getByKey(OilKey key);
        protected void unregister(BlockType type) {
            registeredSet.remove(type);
        }
    }
    
    //static methods
    static {
        BlockTypeHelper h = BlockTypeHelper.getInstance();
        h.apiInit();
        AIR = h.getVanillaBlockType(BlockTypeEnum.AIR);
        GRASS = h.getVanillaBlockType(BlockTypeEnum.GRASS);
        EARTH = h.getVanillaBlockType(BlockTypeEnum.EARTH);
        WOOD = h.getVanillaBlockType(BlockTypeEnum.WOOD);
        STONE = h.getVanillaBlockType(BlockTypeEnum.STONE);
        ORE = h.getVanillaBlockType(BlockTypeEnum.ORE);
        HEAVY = h.getVanillaBlockType(BlockTypeEnum.HEAVY);
        WATER = h.getVanillaBlockType(BlockTypeEnum.WATER);
        LAVA = h.getVanillaBlockType(BlockTypeEnum.LAVA);
        LEAVES = h.getVanillaBlockType(BlockTypeEnum.LEAVES);
        PLANT = h.getVanillaBlockType(BlockTypeEnum.PLANT);
        REPLACEABLE_PLANT = h.getVanillaBlockType(BlockTypeEnum.REPLACEABLE_PLANT);
        SPONGE = h.getVanillaBlockType(BlockTypeEnum.SPONGE);
        CLOTH = h.getVanillaBlockType(BlockTypeEnum.CLOTH);
        FIRE = h.getVanillaBlockType(BlockTypeEnum.FIRE);
        SAND = h.getVanillaBlockType(BlockTypeEnum.SAND);
        ORIENTABLE = h.getVanillaBlockType(BlockTypeEnum.ORIENTABLE);
        WOOL = h.getVanillaBlockType(BlockTypeEnum.ORIENTABLE);
        SHATTERABLE = h.getVanillaBlockType(BlockTypeEnum.SHATTERABLE);
        BUILDABLE_GLASS = h.getVanillaBlockType(BlockTypeEnum.BUILDABLE_GLASS);
        TNT = h.getVanillaBlockType(BlockTypeEnum.TNT);
        CORAL = h.getVanillaBlockType(BlockTypeEnum.CORAL);
        ICE = h.getVanillaBlockType(BlockTypeEnum.ICE);
        SNOW_LAYER = h.getVanillaBlockType(BlockTypeEnum.SNOW_LAYER);
        PACKED_ICE = h.getVanillaBlockType(BlockTypeEnum.PACKED_ICE);
        SNOW_BLOCK = h.getVanillaBlockType(BlockTypeEnum.SNOW_BLOCK);
        CACTUS = h.getVanillaBlockType(BlockTypeEnum.CACTUS);
        CLAY = h.getVanillaBlockType(BlockTypeEnum.CLAY);
        PUMPKIN = h.getVanillaBlockType(BlockTypeEnum.PUMPKIN);
        DRAGON_EGG = h.getVanillaBlockType(BlockTypeEnum.DRAGON_EGG);
        PORTAL = h.getVanillaBlockType(BlockTypeEnum.PORTAL);
        CAKE = h.getVanillaBlockType(BlockTypeEnum.CAKE);
        WEB = h.getVanillaBlockType(BlockTypeEnum.WEB);
        PISTON = h.getVanillaBlockType(BlockTypeEnum.PISTON);
        BANNER = h.getVanillaBlockType(BlockTypeEnum.BANNER);
        UNKNOWN_J = h.getVanillaBlockType(BlockTypeEnum.UNKNOWN_J);
        h.apiPostInit();
    }

    public static BlockType getStandard(BlockTypeEnum type) {
        return enumMap.get(type);
    }


    public static BlockType getByKey(OilKey key) {
        return BlockTypeHelper.getInstance().getByKey(key);
    }

    public static Set<BlockType> getAll() {
        return registeredSetRead;
    }

    /**
     *
     * @param key
     * @return Returns an new BlockType. Be aware that this blocktype will not be supported by any vanilla code
     */
    public static CustomBlockTypeBuilder createBlock(OilKey key) {
        return new CustomBlockTypeBuilder(key);
    }
    
    //fields
    private final NMSBlockType nmsBlockType;
    private final OilKey key;
    private final BlockTypeEnum blockTypeEnum;
    
    //constructor

    protected BlockType(NMSBlockType nmsBlockType, OilKey key, BlockTypeEnum blockTypeEnum) {
        this.nmsBlockType = nmsBlockType;
        this.key = key;
        this.blockTypeEnum = blockTypeEnum;
        if (blockTypeEnum != BlockTypeEnum.CUSTOM && blockTypeEnum != BlockTypeEnum.ENUM_MISSING) {
            enumMap.put(blockTypeEnum, this);
        }
        registeredSet.add(this);
    }

    private BlockType(OilKey key) {
        this.key = key;
        this.blockTypeEnum = BlockTypeEnum.CUSTOM;
        this.nmsBlockType = BlockTypeHelper.getInstance().registerCustom(this);
    }
    
    //methods
    public BlockTypeEnum getBlockTypeEnum() {
        return blockTypeEnum;
    }

    public NMSBlockType getNmsBlockType() {
        return nmsBlockType;
    }

    public OilKey getKey() {
        return key;
    }

    public abstract boolean isLiquid();

    public abstract boolean isBuildable();

    public abstract boolean blocksLight();

    public abstract boolean isSolid();

    public abstract boolean isBurnable();

    public abstract boolean isReplaceable();

    public abstract boolean isAlwaysDestroyable();

    public abstract boolean breakablePickaxe();

    public abstract boolean breakableAxe();

    public abstract boolean breakableShovel();

    public abstract boolean breakableShears();

    public abstract boolean breakableBlade();

    public abstract PistonReaction getPistonReaction();

    //Custom BlockType
    static class CustomBlockType extends BlockType {
        private final boolean liquid;
        private final boolean buildable;
        private final boolean blocksLight;
        private final boolean solid;
        private final boolean burnable;
        private final boolean replaceable;
        private final boolean alwaysDestroyable;
        private final boolean breakablePickaxe;
        private final boolean breakableAxe;
        private final boolean breakableShovel;
        private final boolean breakableShears;
        private final boolean breakableBlade;
        private final PistonReaction pistonReaction;

        CustomBlockType(OilKey key, boolean liquid, boolean buildable, boolean blocksLight, boolean solid, boolean burnable, boolean replaceable, boolean alwaysDestroyable, boolean breakablePickaxe, boolean breakableAxe, boolean breakableShovel, boolean breakableShears, boolean breakableBlade, PistonReaction pistonReaction) {
            super(key);
            this.liquid = liquid;
            this.buildable = buildable;
            this.blocksLight = blocksLight;
            this.solid = solid;
            this.burnable = burnable;
            this.replaceable = replaceable;
            this.alwaysDestroyable = alwaysDestroyable;
            this.breakablePickaxe = breakablePickaxe;
            this.breakableAxe = breakableAxe;
            this.breakableShovel = breakableShovel;
            this.breakableShears = breakableShears;
            this.breakableBlade = breakableBlade;
            this.pistonReaction = pistonReaction;
        }

        @Override
        public boolean isAlwaysDestroyable() {
            return alwaysDestroyable;
        }

        @Override
        public boolean blocksLight() {
            return blocksLight;
        }

        @Override
        public boolean isBuildable() {
            return buildable;
        }

        @Override
        public boolean isBurnable() {
            return burnable;
        }

        @Override
        public boolean isLiquid() {
            return liquid;
        }

        @Override
        public boolean isReplaceable() {
            return replaceable;
        }

        @Override
        public boolean isSolid() {
            return solid;
        }

        @Override
        public PistonReaction getPistonReaction() {
            return pistonReaction;
        }

        @Override
        public boolean breakableAxe() {
            return breakableAxe;
        }

        @Override
        public boolean breakableBlade() {
            return breakableBlade;
        }

        @Override
        public boolean breakablePickaxe() {
            return breakablePickaxe;
        }

        @Override
        public boolean breakableShears() {
            return breakableShears;
        }

        @Override
        public boolean breakableShovel() {
            return breakableShovel;
        }
    }

    //Custom BlockType Builder
    public static class CustomBlockTypeBuilder {
        private OilKey key;
        private boolean liquid;
        private boolean buildable;
        private boolean blocksLight;
        private boolean solid;
        private boolean burnable;
        private boolean replaceable;
        private boolean alwaysDestroyable;
        private PistonReaction pistonReaction;
        private boolean breakablePickaxe;
        private boolean breakableAxe;
        private boolean breakableShovel;
        private boolean breakableShears;
        private boolean breakableBlade;

        public CustomBlockTypeBuilder(OilKey key) {
            this.key = key;
        }

        public CustomBlockTypeBuilder setLiquid() {
            this.liquid = true;
            return this;
        }

        public CustomBlockTypeBuilder setBuildable() {
            this.buildable = true;
            return this;
        }

        public CustomBlockTypeBuilder setBlocksLight() {
            this.blocksLight = true;
            return this;
        }

        public CustomBlockTypeBuilder setSolid() {
            this.solid = true;
            return this;
        }

        public CustomBlockTypeBuilder setBurnable() {
            this.burnable = true;
            return this;
        }

        public CustomBlockTypeBuilder setReplaceable() {
            this.replaceable = true;
            return this;
        }

        public CustomBlockTypeBuilder setAlwaysDestroyable() {
            this.alwaysDestroyable = true;
            return this;
        }

        public CustomBlockTypeBuilder breakablePickaxe() {
            this.alwaysDestroyable = true;
            return this;
        }

        public CustomBlockTypeBuilder breakableAxe() {
            this.alwaysDestroyable = true;
            return this;
        }

        public CustomBlockTypeBuilder breakableShovel() {
            this.alwaysDestroyable = true;
            return this;
        }

        public CustomBlockTypeBuilder breakableShears() {
            this.alwaysDestroyable = true;
            return this;
        }

        public CustomBlockTypeBuilder breakableBlade() {
            this.alwaysDestroyable = true;
            return this;
        }

        public CustomBlockTypeBuilder setPistonReaction(PistonReaction pistonReaction) {
            this.pistonReaction = pistonReaction;
            return this;
        }

        public BlockType.CustomBlockType build() {
            return new BlockType.CustomBlockType(key, liquid, buildable, blocksLight, solid, burnable, replaceable, alwaysDestroyable, breakablePickaxe, breakableAxe, breakableShovel, breakableShears, breakableBlade, pistonReaction);
        }
    }
}
