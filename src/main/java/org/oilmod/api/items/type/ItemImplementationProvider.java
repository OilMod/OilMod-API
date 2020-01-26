package org.oilmod.api.items.type;

import org.oilmod.api.OilMod;
import org.oilmod.api.blocks.type.BlockImplementationProvider;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.registry.KeySettableBase;
import org.oilmod.api.registry.RegistryMPIBase;
import org.oilmod.api.registry.enumpop.*;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.util.IKeyed;

public abstract class ItemImplementationProvider extends EnumPopTypeBase<ItemImplementationProvider, ItemImplementationProvider.TypeEnum, ItemImplementationProvider.Registry, ItemImplementationProvider.MPI, ItemImplementationProvider.Helper<?>> {
    private static final LazyResolver<ItemImplementationProvider, TypeEnum> res = new LazyResolver<>(TypeEnum.class, ()-> ItemImplementationProvider.Helper.getInstance()::getProvider);
    //regex to help convert to new enum reg
    // (public static final )(\w*) (\w*);
    // $1LazyRef<$2, TypeEnum> $3 = new LazyRef<>(res, TypeEnum\.$3);

    //Static members
    public static final LazyRef<ItemImplementationProvider, TypeEnum> PICKAXE = new LazyRef<>(res, TypeEnum.PICKAXE);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> AXE = new LazyRef<>(res, TypeEnum.AXE);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> SHOVEL = new LazyRef<>(res, TypeEnum.SHOVEL);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> SHEARS = new LazyRef<>(res, TypeEnum.SHEARS);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> HOE = new LazyRef<>(res, TypeEnum.HOE);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> TOOL_CUSTOM = new LazyRef<>(res, TypeEnum.TOOL_CUSTOM);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> SWORD = new LazyRef<>(res, TypeEnum.SWORD);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> MELEE_WEAPON_CUSTOM = new LazyRef<>(res, TypeEnum.MELEE_WEAPON_CUSTOM);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> BOW = new LazyRef<>(res, TypeEnum.BOW);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> BOW_CUSTOM = new LazyRef<>(res, TypeEnum.BOW_CUSTOM);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> FOOD = new LazyRef<>(res, TypeEnum.FOOD);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> ARMOR_HELMET = new LazyRef<>(res, TypeEnum.ARMOR_HELMET);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> ARMOR_CHESTPLATE = new LazyRef<>(res, TypeEnum.ARMOR_CHESTPLATE);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> ARMOR_LEGGINGS = new LazyRef<>(res, TypeEnum.ARMOR_LEGGINGS);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> ARMOR_SHOES = new LazyRef<>(res, TypeEnum.ARMOR_SHOES);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> ARMOR_CUSTOM = new LazyRef<>(res, TypeEnum.ARMOR_CUSTOM);
    public static final LazyRef<ItemImplementationProvider, TypeEnum> CUSTOM = new LazyRef<>(res, TypeEnum.CUSTOM);

    /**Registry - for mod-side registering*/
    public static class Registry extends EnumPopRegistry<ItemImplementationProvider, TypeEnum, Registry, MPI, Helper<?>> {
        protected Registry(OilMod mod, Helper<?> registryHelper) {
            super(mod, registryHelper, "oilitem_implementation_provider");
        }
    }


    /**Backing implementation*/
    public static abstract class Helper<Impl extends Helper<Impl>> extends EnumPopRegistryHelperBase<ItemImplementationProvider, TypeEnum, Registry, MPI, Helper<?>, Impl> {
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
        protected LazyResolver<ItemImplementationProvider, TypeEnum> getLazyResolver() {
            return res;
        }

        protected abstract ItemImplementationProvider getProvider(TypeEnum itemType);
    }

    /**MPI - for api meta programming*/
    public static final class MPI extends RegistryMPIBase<ItemImplementationProvider, Registry, MPI, Helper<?>> {}

    /**Enum - for list of things needed to be provided*/
    public enum TypeEnum implements IEnumPopEnum<ItemImplementationProvider, TypeEnum, Registry, MPI, Helper<?>> {
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
    protected ItemImplementationProvider(TypeEnum typeEnum) {
        super(typeEnum);
    }

    //methods

    //abstract methods
    public abstract ItemRep implement(OilItem item);
}
