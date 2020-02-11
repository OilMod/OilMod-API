package org.oilmod.api.blocks.type;

import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.PistonReaction;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.type.IShovel;
import org.oilmod.api.items.type.TBBType;
import org.oilmod.api.registry.IKeySettable;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.item.ItemStateRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.rep.world.WorldRep;
import org.oilmod.api.stateable.IState;
import org.oilmod.api.stateable.IStateable;
import org.oilmod.api.stateable.complex.IComplexState;
import org.oilmod.api.stateable.enumerable.IEnumerableState;
import org.oilmod.api.util.InteractionResult;

import java.util.function.Function;

public interface IBlockGeneric extends IKeySettable, IStateable {
    default PistonReaction getPistonReaction() {
        return PistonReaction.NORMAL;
    }

    BlockType getBlockType();


    default BlockImplementationProvider getImplementationProvider() {
        return BlockImplementationProvider.GENERIC.getValue();
    }

    default boolean isBlocksMovement() {return true;}
    //todo sound
    default int getLightSourceValue() {return 0;}

    default float getResistance() {return 0;}
    default float getHardness() {return 0;}

    default int getHarvestLevel() {return -1;}
    default TBBType getHarvestTool() {return TBBType.NONE.getValue();}

    default float getSlipperiness() {return 0.6f;}

    void setItemFactory(Function<IState, ItemStateRep> factory);
    Function<IState, ItemStateRep> getItemFactory();


    default boolean hasAssociatedItem(IEnumerableState enumState, IComplexState complexState) {return true;}

    default ItemStateRep getAssociatedItem(IEnumerableState enumState, IComplexState complexState) {
        if (hasCustomAssociatedItem()) {
            return getCustomAssociatedItem(enumState, complexState).getDefaultState();
        }
        return getItemFactory().apply(enumState);
    }

    default boolean hasCustomAssociatedItem() {return false;}
    default OilItem getCustomAssociatedItem(IEnumerableState enumState, IComplexState complexState) {
        throw new NotImplementedException("if hasCustomAssociatedItem returns true, getCustomAssociatedItem needs to be overridden");
    }

    default InteractionResult onLeftClickOnBlock(IEnumerableState enumState, IComplexState complexState, EntityHumanRep human, LocationBlockRep loc) { //todo where did the play click (do a lazy way for requesting a ray cast)
        return InteractionResult.NONE;
    }
    default InteractionResult onRightClickOnBlock(IEnumerableState enumState, IComplexState complexState, EntityHumanRep human, LocationBlockRep loc, boolean offhand, BlockFaceRep blockFace, float hitX, float hitY, float hitZ) {return InteractionResult.NONE;}

}
