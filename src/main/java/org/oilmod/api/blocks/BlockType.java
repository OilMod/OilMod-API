package org.oilmod.api.blocks;

import org.oilmod.api.blocks.nms.NMSBlockType;
import org.oilmod.api.rep.block.BlockRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.util.IKeyed;
import org.oilmod.api.util.OilKey;
import org.oilmod.api.util.OilRegistry;

import java.util.EnumMap;
import java.util.Set;

public abstract class BlockType implements IKeyed {
    //Static members
    public static final BlockType AIR;
    public static final BlockType STRUCTURE_VOID;
    public static final BlockType PORTAL;
    public static final BlockType CARPET;
    public static final BlockType PLANTS;
    public static final BlockType OCEAN_PLANT;
    public static final BlockType VINE;
    public static final BlockType SEA_GRASS;
    public static final BlockType WATER;
    public static final BlockType BUBBLE_COLUMN;
    public static final BlockType LAVA;
    public static final BlockType SNOW;
    public static final BlockType FIRE;
    public static final BlockType CIRCUITS;
    public static final BlockType WEB;
    public static final BlockType REDSTONE_LIGHT;
    public static final BlockType CLAY;
    public static final BlockType GROUND;
    public static final BlockType GRASS;
    public static final BlockType PACKED_ICE;
    public static final BlockType SAND;
    public static final BlockType SPONGE;
    public static final BlockType WOOD;
    public static final BlockType CLOTH;
    public static final BlockType TNT;
    public static final BlockType LEAVES;
    public static final BlockType GLASS;
    public static final BlockType ICE;
    public static final BlockType CACTUS;
    public static final BlockType ROCK;
    public static final BlockType IRON;
    public static final BlockType CRAFTED_SNOW;
    public static final BlockType ANVIL;
    public static final BlockType BARRIER;
    public static final BlockType PISTON;
    public static final BlockType CORAL;
    public static final BlockType GOURD;
    public static final BlockType DRAGON_EGG;
    public static final BlockType CAKE;

    private final static EnumMap<BlockTypeEnum, BlockType> enumMap = new EnumMap<>(BlockTypeEnum.class);


    private final static OilRegistry<BlockType> registry = new OilRegistry<>();

    //Enum
    public enum BlockTypeEnum {
        AIR,
        STRUCTURE_VOID,
        PORTAL,
        CARPET,
        PLANTS,
        OCEAN_PLANT,
        VINE,
        SEA_GRASS,
        WATER,
        BUBBLE_COLUMN,
        LAVA,
        SNOW,
        FIRE,
        CIRCUITS,
        WEB,
        REDSTONE_LIGHT,
        CLAY,
        GROUND,
        GRASS,
        PACKED_ICE,
        SAND,
        SPONGE,
        WOOD,
        CLOTH,
        TNT,
        LEAVES,
        GLASS,
        ICE,
        CACTUS,
        ROCK,
        IRON,
        CRAFTED_SNOW,
        ANVIL,
        BARRIER,
        PISTON,
        CORAL,
        GOURD,
        DRAGON_EGG,
        CAKE,
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
                        BlockType.init();
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
        protected void unregister(OilKey key) {
            registry.unregister(key);
        }
    }
    
    //static methods
    static {
        BlockTypeHelper h = BlockTypeHelper.getInstance();
        h.apiInit();
        AIR = h.getVanillaBlockType(BlockTypeEnum.AIR);
        STRUCTURE_VOID = h.getVanillaBlockType(BlockTypeEnum.STRUCTURE_VOID);
        PORTAL = h.getVanillaBlockType(BlockTypeEnum.PORTAL);
        CARPET = h.getVanillaBlockType(BlockTypeEnum.CARPET);
        PLANTS = h.getVanillaBlockType(BlockTypeEnum.PLANTS);
        OCEAN_PLANT = h.getVanillaBlockType(BlockTypeEnum.OCEAN_PLANT);
        VINE = h.getVanillaBlockType(BlockTypeEnum.VINE);
        SEA_GRASS = h.getVanillaBlockType(BlockTypeEnum.SEA_GRASS);
        WATER = h.getVanillaBlockType(BlockTypeEnum.WATER);
        BUBBLE_COLUMN = h.getVanillaBlockType(BlockTypeEnum.BUBBLE_COLUMN);
        LAVA = h.getVanillaBlockType(BlockTypeEnum.LAVA);
        SNOW = h.getVanillaBlockType(BlockTypeEnum.SNOW);
        FIRE = h.getVanillaBlockType(BlockTypeEnum.FIRE);
        CIRCUITS = h.getVanillaBlockType(BlockTypeEnum.CIRCUITS);
        WEB = h.getVanillaBlockType(BlockTypeEnum.WEB);
        REDSTONE_LIGHT = h.getVanillaBlockType(BlockTypeEnum.REDSTONE_LIGHT);
        CLAY = h.getVanillaBlockType(BlockTypeEnum.CLAY);
        GROUND = h.getVanillaBlockType(BlockTypeEnum.GROUND);
        GRASS = h.getVanillaBlockType(BlockTypeEnum.GRASS);
        PACKED_ICE = h.getVanillaBlockType(BlockTypeEnum.PACKED_ICE);
        SAND = h.getVanillaBlockType(BlockTypeEnum.SAND);
        SPONGE = h.getVanillaBlockType(BlockTypeEnum.SPONGE);
        WOOD = h.getVanillaBlockType(BlockTypeEnum.WOOD);
        CLOTH = h.getVanillaBlockType(BlockTypeEnum.CLOTH);
        TNT = h.getVanillaBlockType(BlockTypeEnum.TNT);
        LEAVES = h.getVanillaBlockType(BlockTypeEnum.LEAVES);
        GLASS = h.getVanillaBlockType(BlockTypeEnum.GLASS);
        ICE = h.getVanillaBlockType(BlockTypeEnum.ICE);
        CACTUS = h.getVanillaBlockType(BlockTypeEnum.CACTUS);
        ROCK = h.getVanillaBlockType(BlockTypeEnum.ROCK);
        IRON = h.getVanillaBlockType(BlockTypeEnum.IRON);
        CRAFTED_SNOW = h.getVanillaBlockType(BlockTypeEnum.CRAFTED_SNOW);
        ANVIL = h.getVanillaBlockType(BlockTypeEnum.ANVIL);
        BARRIER = h.getVanillaBlockType(BlockTypeEnum.BARRIER);
        PISTON = h.getVanillaBlockType(BlockTypeEnum.PISTON);
        CORAL = h.getVanillaBlockType(BlockTypeEnum.CORAL);
        GOURD = h.getVanillaBlockType(BlockTypeEnum.GOURD);
        DRAGON_EGG = h.getVanillaBlockType(BlockTypeEnum.DRAGON_EGG);
        CAKE = h.getVanillaBlockType(BlockTypeEnum.CAKE);
        h.apiPostInit();
    }

    private static void init() {}

    public static BlockType getStandard(BlockTypeEnum type) {
        return enumMap.get(type);
    }


    public static BlockType getByKey(OilKey key) {
        return registry.get(key);
    }

    public static Set<BlockType> getAll() {
        return registry.getRegistered();
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
        registry.register(this);
    }

    private BlockType(OilKey key) {
        this.key = key;
        this.blockTypeEnum = BlockTypeEnum.CUSTOM;
        this.nmsBlockType = BlockTypeHelper.getInstance().registerCustom(this);
        registry.register(this);
    }
    
    //methods
    public BlockTypeEnum getBlockTypeEnum() {
        return blockTypeEnum;
    }

    public NMSBlockType getNmsBlockType() {
        return nmsBlockType;
    }

    public OilKey getOilKey() {
        return key;
    }

    public abstract boolean isLiquid();

    public abstract boolean isBuildable();

    public abstract boolean isOpaque();

    public abstract boolean isSolid();

    public abstract boolean isFlammable();

    public abstract boolean isReplaceable();

    public abstract boolean isAlwaysDestroyable();

    @Deprecated
    public abstract boolean breakablePickaxe(BlockStateRep material);

    @Deprecated
    public abstract boolean breakableAxe(BlockStateRep material);

    @Deprecated
    public abstract boolean breakableShovel(BlockStateRep material);

    @Deprecated
    public abstract boolean breakableShears(BlockStateRep material);

    @Deprecated
    public abstract boolean breakableBlade(BlockStateRep material);

    public abstract PistonReaction getPistonReaction();

    public abstract MapColor getColor();

    //Custom BlockType
    public static class CustomBlockType extends BlockType {
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
        private final MapColor color;

        public CustomBlockType(OilKey key, MapColor color, boolean liquid, boolean buildable, boolean blocksLight, boolean solid, boolean burnable, boolean replaceable, boolean alwaysDestroyable, boolean breakablePickaxe, boolean breakableAxe, boolean breakableShovel, boolean breakableShears, boolean breakableBlade, PistonReaction pistonReaction) {
            super(key);
            this.color = color;
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

        public CustomBlockType(CustomBlockTypeBuilder builder) {
            this(builder.key, builder.color, builder.liquid, builder.buildable, builder.blocksLight, builder.solid, builder.burnable, builder.replaceable, builder.alwaysDestroyable, builder.breakablePickaxe, builder.breakableAxe, builder.breakableShovel, builder.breakableShears, builder.breakableBlade, builder.pistonReaction);
        }

        @Override
        public boolean isAlwaysDestroyable() {
            return alwaysDestroyable;
        }

        @Override
        public boolean isOpaque() {
            return blocksLight;
        }

        @Override
        public boolean isBuildable() {
            return buildable;
        }

        @Override
        public boolean isFlammable() {
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
        public MapColor getColor() {
            return color;
        }

        @Override
        public boolean breakableAxe(BlockStateRep material) {
            return breakableAxe;
        }

        @Override
        public boolean breakableBlade(BlockStateRep material) {
            return breakableBlade;
        }

        @Override
        public boolean breakablePickaxe(BlockStateRep material) {
            return breakablePickaxe;
        }

        @Override
        public boolean breakableShears(BlockStateRep material) {
            return breakableShears;
        }

        @Override
        public boolean breakableShovel(BlockStateRep material) {
            return breakableShovel;
        }
    }

    //Custom BlockType Builder
    public static class CustomBlockTypeBuilder {
        private OilKey key;
        private MapColor color; //TODO: assign standard later when MapColor actually exists
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

        public CustomBlockTypeBuilder setColor(MapColor color) {
            this.color = color;
            return this;
        }

        public BlockType.CustomBlockType build() {
            return new BlockType.CustomBlockType(this);
        }
    }
}
