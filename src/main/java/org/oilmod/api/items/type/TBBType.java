package org.oilmod.api.items.type;

import org.oilmod.api.OilMod;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.registry.KeySettableBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.registry.enumpop.*;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.util.InteractionResult;


public abstract class TBBType extends EnumPopTypeBase<TBBType, TBBType.TBBEnum, TBBType.Registry, TBBType.MPI, TBBType.Helper<?>> {
    private static final LazyResolver<TBBType, TBBEnum> res = new LazyResolver<>(TBBEnum.class, ()-> TBBType.Helper.getInstance()::getProvider);
    //regex to help convert to new enum reg
    // (public static final )(\w*) (\w*);
    // $1LazyRef<$2, TBBEnum> $3 = new LazyRef<>(res, TBBEnum\.$3);

    //Static members
    public static final LazyRef<TBBType, TBBEnum> PICKAXE = new LazyRef<>(res, TBBEnum.PICKAXE);
    public static final LazyRef<TBBType, TBBEnum> AXE = new LazyRef<>(res, TBBEnum.AXE);
    public static final LazyRef<TBBType, TBBEnum> SHOVEL = new LazyRef<>(res, TBBEnum.SHOVEL);
    public static final LazyRef<TBBType, TBBEnum> SHEARS = new LazyRef<>(res, TBBEnum.SHEARS);
    public static final LazyRef<TBBType, TBBEnum> SWORD = new LazyRef<>(res, TBBEnum.SWORD);
    public static final LazyRef<TBBType, TBBEnum> NONE = new LazyRef<>(res, TBBEnum.NONE);

    /**Registry - for mod-side registering*/
    public static class Registry extends EnumPopRegistry<TBBType, TBBEnum, Registry, MPI, Helper<?>> {
        protected Registry(OilMod mod, Helper<?> registryHelper) {
            super(mod, registryHelper, "tool_block_breaking_provider");
        }
    }


    /**Backing implementation*/
    public static abstract class Helper<Impl extends Helper<Impl>> extends EnumPopRegistryHelperBase<TBBType, TBBEnum, Registry, MPI, Helper<?>, Impl> {
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
        protected LazyResolver<TBBType, TBBEnum> getLazyResolver() {
            return res;
        }

        protected abstract TBBType getProvider(TBBEnum itemType);
    }

    /**MPI - for api meta programming*/
    public static final class MPI extends RegistryMPIBase<TBBType, Registry, MPI, Helper<?>> {}

    /**Enum - for list of things needed to be provided*/
    public enum TBBEnum implements IEnumPopEnum<TBBType, TBBEnum, Registry, MPI, Helper<?>> {
        PICKAXE,
        AXE,
        SHOVEL,
        SHEARS,
        SWORD,
        NONE,
        ENUM_MISSING,
        CUSTOM;
        @Override
        public TBBEnum missing() {
            return ENUM_MISSING;
        }

        @Override
        public TBBEnum custom() {
            return CUSTOM;
        }

        @Override
        public TBBType.Helper<?> getProvider() {
            return TBBType.Helper.getInstance();
        }
    }



    //fields

    //constructor
    protected TBBType(TBBEnum tbbEnum) {
        super(tbbEnum);
    }

    //methods

    //abstract methods
    protected abstract boolean canHarvestBlock(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, BlockType blockType);
    protected abstract float getDestroySpeed(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, BlockType blockType);
    protected abstract boolean onEntityHit(IToolBlockBreaking  item, OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker);
    protected abstract boolean onBlockDestroyed(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, LocationBlockRep location, EntityLivingRep entityLiving);
    protected abstract InteractionResult onItemUseOnBlock(IToolBlockBreaking item, OilItemStack stack, EntityLivingRep humanEntity, LocationBlockRep pos, boolean offhand, BlockFaceRep facing, float hitX, float hitY, float hitZ);
    protected abstract ItemImplementationProvider getImplementationProvider();
}
