package org.oilmod.api.items.type;

import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.world.LocationBlockRep;

public interface IToolBlockBreaking extends ITool {
    TBBType getTbbType();

    default boolean canHarvestBlock(OilItemStack itemStack, BlockStateRep blockState, BlockType blockType) {
        return getTbbType().canHarvestBlock(this, itemStack, blockState, blockType);
    }

    default float getDestroySpeed(OilItemStack itemStack, BlockStateRep blockState, BlockType blockType) {
        return getTbbType().getDestroySpeed(this, itemStack, blockState, blockType);
    }

    float getDestroySpeed(OilItemStack itemStack);


    @Override
    default boolean onEntityHit(OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker) {
        return getTbbType().onEntityHit(this, stack , target, attacker);
    }

    @Override
    default boolean onBlockDestroyed(OilItemStack stack, BlockStateRep blockState, LocationBlockRep pos, EntityLivingRep entityLiving) {
        return getTbbType().onBlockDestroyed(this, stack, blockState, pos, entityLiving);
    }


    default int getToolStrength(OilItemStack stack, TBBType tooltype) {return 1;}

    @Override
    int getItemEnchantability();

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.DIGGER;
    }


    default ItemImplementationProvider getImplementationProvider() {
        return getTbbType().getImplementationProvider();
    }
}