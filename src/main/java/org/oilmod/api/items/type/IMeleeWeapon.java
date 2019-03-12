package org.oilmod.api.items.type;

import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.world.LocationBlockRep;

public interface IMeleeWeapon extends IWeapon {

    @Override
    default boolean onEntityHit(OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker) {return false;}//TODO

    @Override
    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, LocationBlockRep pos, EntityLivingRep entityLiving) {return true;}



    @Override
    int getItemEnchantability();

    @Override
    EnchantmentType getEnchantmentType();


    default ImplementationProvider getImplementationProvider() {
        return ImplementationProvider.MELEE_WEAPON_CUSTOM;
    }
}
