package org.oilmod.api.items.type;

import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.IBlockState;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.world.LocationBlockRep;

@SuppressWarnings("unchecked")
public interface IToolBlockBreaking extends ITool {
    TBBType getTbbType();

    default boolean canHarvestBlock(IBlockState blockState, BlockType blockType) {
        return getTbbType().canHarvestBlock(this, blockState, blockType);
    }

    default float getDestroySpeed(OilItemStack itemStack, IBlockState blockState, BlockType blockType) {
        return getTbbType().getDestroySpeed(this, itemStack, blockState, blockType);
    }

    float getDestroySpeed(OilItemStack itemStack);


    @Override
    default boolean onEntityHit(OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker) {
        return getTbbType().onEntityHit(this, stack , target, attacker);
    }

    @Override
    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, LocationBlockRep pos, EntityLivingRep entityLiving) {
        return getTbbType().onBlockDestroyed(this, stack, blockState, pos, entityLiving);
    }


    @Override
    int getItemEnchantability();

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.DIGGER;
    }


    default ImplementationProvider getImplementationProvider() {
        return getTbbType().getImplementationProvider();
    }
}