package org.oilmod.api.items.type;

import gnu.trove.set.hash.THashSet;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.util.InteractionResult;

import java.util.Collections;
import java.util.Set;

public abstract class TBBType {
    //Static members
    public static final TBBType PICKAXE; //removing final here gives more flexibility but requires to also change vanilla behaviour TODO: think about it
    public static final TBBType AXE;
    public static final TBBType SHOVEL;
    public static final TBBType SHEARS;
    public static final TBBType SWORD;

    private final static Set<TBBType> registeredSet = new THashSet<>();
    private final static Set<TBBType> registeredSetRead = Collections.unmodifiableSet(registeredSet);

    //Enum
    public enum TBBEnum {
        PICKAXE,
        AXE,
        SHOVEL,
        SHEARS,
        SWORD,
        CUSTOM
    }

    //Backing implementation
    public static abstract class TBBHelper {
        private static TBBHelper instance;
        private static final Object MUTEX = new Object();
        private static final String CANNOT_INITIALISE_SINGLETON_TWICE = "Cannot initialise singleton twice!";

        public static void setInstance(TBBHelper instance) {
            if (TBBHelper.instance == null) {
                synchronized (MUTEX) {
                    if (TBBHelper.instance == null) {
                        TBBHelper.instance = instance;
                    } else {
                        throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
                    }
                }
            } else {
                throw new IllegalStateException(CANNOT_INITIALISE_SINGLETON_TWICE);
            }
        }

        public static TBBHelper getInstance() {
            return instance;
        }
        protected abstract void apiInit(); //prepare stuff
        protected abstract void apiPostInit(); //dunno for this one
        protected abstract TBBType getVanilla(TBBEnum itemType);
        protected void unregister(TBBType itemType) {
            registeredSet.remove(itemType);
        }
    }

    //static methods
    static {
        TBBHelper h = TBBHelper.getInstance();
        h.apiInit();
        PICKAXE = h.getVanilla(TBBEnum.PICKAXE);
        AXE = h.getVanilla(TBBEnum.AXE);
        SHOVEL = h.getVanilla(TBBEnum.SHOVEL);
        SHEARS = h.getVanilla(TBBEnum.SHEARS);
        SWORD = h.getVanilla(TBBEnum.SWORD);
        h.apiPostInit();
    }

    public static Set<TBBType> getAll() {
        return registeredSetRead;
    }


    //fields
    private final TBBEnum tbbEnum;

    //constructor
    protected TBBType(TBBEnum tbbEnum) {
        this.tbbEnum = tbbEnum;
        registeredSet.add(this);
    }

    //methods
    public TBBEnum getTbbEnum() {
        return tbbEnum;
    }

    //abstract methods
    protected abstract boolean canHarvestBlock(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, BlockType blockType);
    protected abstract float getDestroySpeed(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, BlockType blockType);
    protected abstract boolean onEntityHit(IToolBlockBreaking  item, OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker);
    protected abstract boolean onBlockDestroyed(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, LocationBlockRep location, EntityLivingRep entityLiving);
    protected abstract InteractionResult onItemUseOnBlock(IToolBlockBreaking item, OilItemStack stack, EntityLivingRep humanEntity, LocationBlockRep pos, boolean offhand, BlockFaceRep facing, float hitX, float hitY, float hitZ);
    protected abstract ImplementationProvider getImplementationProvider();
}
