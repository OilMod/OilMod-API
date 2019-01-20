package org.oilmod.api.items.type;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.IBlockState;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItemStack;

@SuppressWarnings("unchecked")
public interface IToolBlockBreaking extends ITool {
    TBBType getTbbType();

    default boolean canHarvestBlock(BlockState blockState, BlockType blockType) {
        return getTbbType().canHarvestBlock(this, blockState, blockType);
    }

    default float getDestroySpeed(OilItemStack itemStack, BlockState blockState, BlockType blockType) {
        return getTbbType().getDestroySpeed(this, itemStack, blockState, blockType);
    }

    float getDestroySpeed(OilItemStack itemStack);


    @Override
    default boolean onEntityHit(OilItemStack stack, LivingEntity target, LivingEntity attacker) {
        return getTbbType().onEntityHit(this, stack , target, attacker);
    }

    @Override
    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, Location pos, LivingEntity entityLiving) {
        return getTbbType().onBlockDestroyed(this, stack, blockState, pos, entityLiving);
    }


    @Override
    int getItemEnchantability();

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.DIGGER;
    }
}