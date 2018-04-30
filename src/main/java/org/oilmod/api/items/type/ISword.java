package org.oilmod.api.items.type;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.IBlockState;
import org.oilmod.api.items.OilItemStack;

public interface ISword extends IMeleeWeapon, IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.SWORD;}


    @Override
    default boolean onEntityHit(OilItemStack stack, LivingEntity target, LivingEntity attacker) {
        return IMeleeWeapon.super.onEntityHit(stack, target, attacker);
    }

    @Override
    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, Location pos, LivingEntity entityLiving) {
        return IMeleeWeapon.super.onBlockDestroyed(stack, blockState, pos, entityLiving);
    }

    @Override
    default int getMaxStackSize() {
        return IMeleeWeapon.super.getMaxStackSize();
    }

    @Override
    default float getDestroySpeed(OilItemStack itemStack) {
        return 1.5f;
    }
}