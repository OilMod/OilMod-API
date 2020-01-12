package org.oilmod.api.blocks;

import org.oilmod.api.OilMod;
import org.oilmod.api.blocks.nms.NMSBlockType;
import org.oilmod.api.registry.RegistryHelperBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.registry.enumpop.*;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.util.IKeyed;
import org.oilmod.api.util.OilKey;
import org.oilmod.api.util.OilRegistry;

import java.util.EnumMap;
import java.util.Set;

public abstract class BlockType implements IKeyed, IEnumPopType<BlockType, BlockType.BlockTypeEnum, BlockType.Registry, BlockType.MPI, BlockType.Helper<?>> {
    private static final LazyResolver<BlockType, BlockTypeEnum> res = new LazyResolver<>(BlockTypeEnum.class, ()->Helper.getInstance()::getVanillaBlockType);
    //Static members
    public static final LazyRef<BlockType, BlockTypeEnum> AIR = new LazyRef<>(res, BlockTypeEnum.AIR);
    public static final LazyRef<BlockType, BlockTypeEnum> STRUCTURE_VOID = new LazyRef<>(res, BlockTypeEnum.STRUCTURE_VOID);
    public static final LazyRef<BlockType, BlockTypeEnum> PORTAL = new LazyRef<>(res, BlockTypeEnum.PORTAL);
    public static final LazyRef<BlockType, BlockTypeEnum> CARPET = new LazyRef<>(res, BlockTypeEnum.CARPET);
    public static final LazyRef<BlockType, BlockTypeEnum> PLANTS = new LazyRef<>(res, BlockTypeEnum.PLANTS);
    public static final LazyRef<BlockType, BlockTypeEnum> OCEAN_PLANT = new LazyRef<>(res, BlockTypeEnum.OCEAN_PLANT);
    public static final LazyRef<BlockType, BlockTypeEnum> VINE = new LazyRef<>(res, BlockTypeEnum.VINE);
    public static final LazyRef<BlockType, BlockTypeEnum> SEA_GRASS = new LazyRef<>(res, BlockTypeEnum.SEA_GRASS);
    public static final LazyRef<BlockType, BlockTypeEnum> WATER = new LazyRef<>(res, BlockTypeEnum.WATER);
    public static final LazyRef<BlockType, BlockTypeEnum> BUBBLE_COLUMN = new LazyRef<>(res, BlockTypeEnum.BUBBLE_COLUMN);
    public static final LazyRef<BlockType, BlockTypeEnum> LAVA = new LazyRef<>(res, BlockTypeEnum.LAVA);
    public static final LazyRef<BlockType, BlockTypeEnum> SNOW = new LazyRef<>(res, BlockTypeEnum.SNOW);
    public static final LazyRef<BlockType, BlockTypeEnum> FIRE = new LazyRef<>(res, BlockTypeEnum.FIRE);
    public static final LazyRef<BlockType, BlockTypeEnum> CIRCUITS = new LazyRef<>(res, BlockTypeEnum.CIRCUITS);
    public static final LazyRef<BlockType, BlockTypeEnum> WEB = new LazyRef<>(res, BlockTypeEnum.WEB);
    public static final LazyRef<BlockType, BlockTypeEnum> REDSTONE_LIGHT = new LazyRef<>(res, BlockTypeEnum.REDSTONE_LIGHT);
    public static final LazyRef<BlockType, BlockTypeEnum> CLAY = new LazyRef<>(res, BlockTypeEnum.CLAY);
    public static final LazyRef<BlockType, BlockTypeEnum> GROUND = new LazyRef<>(res, BlockTypeEnum.GROUND);
    public static final LazyRef<BlockType, BlockTypeEnum> GRASS = new LazyRef<>(res, BlockTypeEnum.GRASS);
    public static final LazyRef<BlockType, BlockTypeEnum> PACKED_ICE = new LazyRef<>(res, BlockTypeEnum.PACKED_ICE);
    public static final LazyRef<BlockType, BlockTypeEnum> SAND = new LazyRef<>(res, BlockTypeEnum.SAND);
    public static final LazyRef<BlockType, BlockTypeEnum> SPONGE = new LazyRef<>(res, BlockTypeEnum.SPONGE);
    public static final LazyRef<BlockType, BlockTypeEnum> WOOD = new LazyRef<>(res, BlockTypeEnum.WOOD);
    public static final LazyRef<BlockType, BlockTypeEnum> CLOTH = new LazyRef<>(res, BlockTypeEnum.CLOTH);
    public static final LazyRef<BlockType, BlockTypeEnum> TNT = new LazyRef<>(res, BlockTypeEnum.TNT);
    public static final LazyRef<BlockType, BlockTypeEnum> LEAVES = new LazyRef<>(res, BlockTypeEnum.LEAVES);
    public static final LazyRef<BlockType, BlockTypeEnum> GLASS = new LazyRef<>(res, BlockTypeEnum.GLASS);
    public static final LazyRef<BlockType, BlockTypeEnum> ICE = new LazyRef<>(res, BlockTypeEnum.ICE);
    public static final LazyRef<BlockType, BlockTypeEnum> CACTUS = new LazyRef<>(res, BlockTypeEnum.CACTUS);
    public static final LazyRef<BlockType, BlockTypeEnum> ROCK = new LazyRef<>(res, BlockTypeEnum.ROCK);
    public static final LazyRef<BlockType, BlockTypeEnum> IRON = new LazyRef<>(res, BlockTypeEnum.IRON);
    public static final LazyRef<BlockType, BlockTypeEnum> CRAFTED_SNOW = new LazyRef<>(res, BlockTypeEnum.CRAFTED_SNOW);
    public static final LazyRef<BlockType, BlockTypeEnum> ANVIL = new LazyRef<>(res, BlockTypeEnum.ANVIL);
    public static final LazyRef<BlockType, BlockTypeEnum> BARRIER = new LazyRef<>(res, BlockTypeEnum.BARRIER);
    public static final LazyRef<BlockType, BlockTypeEnum> PISTON = new LazyRef<>(res, BlockTypeEnum.PISTON);
    public static final LazyRef<BlockType, BlockTypeEnum> CORAL = new LazyRef<>(res, BlockTypeEnum.CORAL);
    public static final LazyRef<BlockType, BlockTypeEnum> GOURD = new LazyRef<>(res, BlockTypeEnum.GOURD);
    public static final LazyRef<BlockType, BlockTypeEnum> DRAGON_EGG = new LazyRef<>(res, BlockTypeEnum.DRAGON_EGG);
    public static final LazyRef<BlockType, BlockTypeEnum> CAKE = new LazyRef<>(res, BlockTypeEnum.CAKE);

    /**Registry - for mod-side registering*/
    public static class Registry extends EnumPopRegistry<BlockType, BlockTypeEnum, Registry, MPI, Helper<?>> {
        protected Registry(OilMod mod, Helper<?> registryHelper) {
            super(mod, registryHelper, "oilblocktype");
        }
    }


    /**Backing implementation*/
    public static abstract class Helper<Impl extends Helper<Impl>> extends EnumPopRegistryHelperBase<BlockType, BlockTypeEnum, Registry, MPI, Helper<?>, Impl> {
        private static Helper<?> instance;

        public Helper() {
            super();
        }

        @Override
        protected void setSingleton() {
            instance = this;
        }

        public static Helper<?> getInstance() {
            return instance;
        }

        @Override
        public Registry create(OilMod mod) {
            return new Registry(mod, this);
        }

        @Override
        protected LazyResolver<BlockType, BlockTypeEnum> getLazyResolver() {
            return res;
        }

        protected abstract BlockType getVanillaBlockType(BlockTypeEnum blockType);
        protected abstract NMSBlockType registerCustom(BlockType blockType);
    }

    /**MPI - for api meta programming*/
    public static final class MPI extends RegistryMPIBase<BlockType, Registry, MPI, Helper<?>> {}
    
    //Enum*/
    public enum BlockTypeEnum implements IEnumPopEnum<BlockType, BlockTypeEnum, Registry, MPI, Helper<?>> {
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

        @Override
        public BlockTypeEnum missing() {
            return ENUM_MISSING;
        }

        @Override
        public BlockTypeEnum custom() {
            return CUSTOM;
        }

        @Override
        public Helper<?> getProvider() {
            return Helper.getInstance();
        }
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
    }

    private BlockType(OilKey key) {
        this.key = key;
        this.blockTypeEnum = BlockTypeEnum.CUSTOM;
        this.nmsBlockType = Helper.getInstance().registerCustom(this);
    }
    
    //methods
    @Override
    public BlockTypeEnum getTypeEnum() {
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
