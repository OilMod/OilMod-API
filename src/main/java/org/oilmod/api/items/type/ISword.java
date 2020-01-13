package org.oilmod.api.items.type;

import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.world.LocationBlockRep;

public interface ISword extends IMeleeWeapon, IToolBlockBreaking {
    @Override
    default TBBType getTbbType() {return TBBType.SWORD;}


    @Override
    default boolean onEntityHit(OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker) {
        return IMeleeWeapon.super.onEntityHit(stack, target, attacker);
    }

    @Override
    default boolean onBlockDestroyed(OilItemStack stack, BlockStateRep blockState, LocationBlockRep pos, EntityLivingRep entityLiving) {
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



    @Override
    int getItemEnchantability();

    @Override
    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.WEAPON;
    }

    default ItemImplementationProvider getImplementationProvider() {
        return getTbbType().getImplementationProvider();
    }
}