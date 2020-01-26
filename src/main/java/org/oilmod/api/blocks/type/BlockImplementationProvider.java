package org.oilmod.api.blocks.type;

import org.oilmod.api.OilMod;
import org.oilmod.api.blocks.OilBlock;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.registry.KeySettableBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.registry.enumpop.*;
import org.oilmod.api.rep.block.BlockRep;
import org.oilmod.api.util.IKeyed;

public abstract class BlockImplementationProvider extends EnumPopTypeBase<BlockImplementationProvider, BlockImplementationProvider.TypeEnum, BlockImplementationProvider.Registry, BlockImplementationProvider.MPI, BlockImplementationProvider.Helper<?>> {
    private static final LazyResolver<BlockImplementationProvider, TypeEnum> res = new LazyResolver<>(TypeEnum.class, ()-> BlockImplementationProvider.Helper.getInstance()::getProvider);
    //regex to help convert to new enum reg
    // (public static final )(\w*) (\w*);
    // $1LazyRef<$2, TypeEnum> $3 = new LazyRef<>(res, TypeEnum\.$3);
    
    //Static members
    public static final LazyRef<BlockImplementationProvider, TypeEnum> GENERIC = new LazyRef<>(res, TypeEnum.GENERIC);
    public static final LazyRef<BlockImplementationProvider, TypeEnum> CUSTOM = new LazyRef<>(res, TypeEnum.CUSTOM);

    /**Registry - for mod-side registering*/
    public static class Registry extends EnumPopRegistry<BlockImplementationProvider, TypeEnum, Registry, MPI, Helper<?>> {
        protected Registry(OilMod mod, Helper<?> registryHelper) {
            super(mod, registryHelper, "oilblock_implementation_provider");
        }
    }


    /**Backing implementation*/
    public static abstract class Helper<Impl extends Helper<Impl>> extends EnumPopRegistryHelperBase<BlockImplementationProvider, TypeEnum, Registry, MPI, Helper<?>, Impl> {
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
        protected LazyResolver<BlockImplementationProvider, TypeEnum> getLazyResolver() {
            return res;
        }

        protected abstract BlockImplementationProvider getProvider(TypeEnum itemType);
    }

    /**MPI - for api meta programming*/
    public static final class MPI extends RegistryMPIBase<BlockImplementationProvider, Registry, MPI, Helper<?>> {}

    /**Enum - for list of things needed to be provided*/
    public enum TypeEnum implements IEnumPopEnum<BlockImplementationProvider, TypeEnum, Registry, MPI, Helper<?>> {
        GENERIC,
        ENUM_MISSING,
        CUSTOM;
        @Override
        public TypeEnum missing() {
            return ENUM_MISSING;
        }

        @Override
        public TypeEnum custom() {
            return CUSTOM;
        }

        @Override
        public boolean isSpecial() {
            return isMissing(); //we want to request a std impl for custom!
        }

        @Override
        public Helper<?> getProvider() {
            return Helper.getInstance();
        }
    }

    //fields

    //constructor
    protected BlockImplementationProvider(TypeEnum typeEnum) {
        super(typeEnum);
    }

    //methods

    //abstract methods
    public abstract BlockRep implement(OilBlock block);
}
