package org.oilmod.api.items.type;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.IBlockState;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItemStack;

public interface IMeleeWeapon extends IWeapon {

    @Override
    default boolean onEntityHit(OilItemStack stack, LivingEntity target, LivingEntity attacker) {return false;}//TODO

    @Override
    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, Location pos, LivingEntity entityLiving) {return true;}



    @Override
    int getItemEnchantability();

    @Override
    EnchantmentType getEnchantmentType();
}
