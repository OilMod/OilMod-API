package org.oilmod.api.items.type;

import org.apache.commons.lang3.NotImplementedException;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.registry.IKeySettable;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.enchant.EnchantmentRep;
import org.oilmod.api.rep.entity.EntityHumanRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.rep.world.WorldRep;
import org.oilmod.api.util.InteractionResult;

public interface IItemGeneric extends IKeySettable {

    default boolean onEntityHit(OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker) {
        if (this instanceof ISword) throw new NotImplementedException("call ISword.super.onEntityHit etc instead");
        if (this instanceof IToolBlockBreaking) throw new NotImplementedException("call IToolBlockBreaking.super.onEntityHit etc instead");
        if (this instanceof IMeleeWeapon) throw new NotImplementedException("call IMeleeWeapon.super.onEntityHit etc instead");
        return true;
    }

    default boolean onBlockDestroyed(OilItemStack stack, BlockStateRep blockState, LocationBlockRep pos, EntityLivingRep entityLiving) {
        if (this instanceof ISword) throw new NotImplementedException("call ISword.super.onBlockDestroyed etc instead");
        if (this instanceof IToolBlockBreaking) throw new NotImplementedException("call IToolBlockBreaking.super.onBlockDestroyed etc instead");
        if (this instanceof IMeleeWeapon) throw new NotImplementedException("call IMeleeWeapon.super.onBlockDestroyed etc instead");
        return true;
    }


    default InteractionResult onItemUseOnBlock(OilItemStack stack, EntityHumanRep human, LocationBlockRep loc, boolean offhand, BlockFaceRep blockFace, float hitX, float hitY, float hitZ) {
        if (this instanceof IShovel) throw new NotImplementedException("call IShovel.super.onItemUseOnBlock etc instead");
        return InteractionResult.NONE;
    }
    default InteractionResult onItemLeftClickOnBlock(OilItemStack stack, EntityHumanRep human, LocationBlockRep loc, boolean offhand, BlockFaceRep blockFace, float hitX, float hitY, float hitZ) {return InteractionResult.NONE;}

    default ItemInteractionResult onItemRightClick(OilItemStack stack, WorldRep world, EntityHumanRep human, boolean offhand) {return new ItemInteractionResult(InteractionResult.NONE, stack.asBukkitItemStack());}
    default ItemInteractionResult onItemLeftClick(OilItemStack stack, WorldRep world, EntityHumanRep human, boolean offhand) {return new ItemInteractionResult(InteractionResult.NONE, stack.asBukkitItemStack());}

    default boolean handleItemDamage(OilItemStack stack, int damage, EntityLivingRep entity) {
        if (this instanceof IDurable) throw new NotImplementedException("call IDurable.super.handleItemDamage etc instead");
        return false;
    }

    default void damageItem(OilItemStack stack, int damage, EntityLivingRep entity) {
        if (this instanceof IDurable) throw new NotImplementedException("call IDurable.super.damageItem etc instead");
    }

    default int getItemDamage(OilItemStack stack) {
        if (this instanceof IDurable) throw new NotImplementedException("call IDurable.super.getItemDamage etc instead");
        return 0;
    }

    /**
     *
     * @return returns the maximal stack size of this item
     */
    default int getMaxStackSize() {
        return 64;
    }

    /**
     * reaches normally from 5 (stone) to 22 (gold). Higher value gives better enchantments
     * @return
     */
    default int getItemEnchantability() {return 0;}

    default EnchantmentType getEnchantmentType() {
        return EnchantmentType.NONE;
    }

    /**
     * Repairable materials might be used for crafting, anvils etc. this is for vanilla like behavior
     * */
    default boolean isRepairable(OilItemStack toRepair, ItemStackRep repair) {return false;}


    //TODO fix this javadoc. fix ll the javadocs
    /**
     * Called when a preview of the result is needed. This code should under no circumstances have any side effects.
     * Should modify the OilItemStack in a way that it looks visually like the ItemStackRep after the repair.
     * This should only include lightweight and fast algorithms. This method should not modify the ingredients in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a repair is really executed.
     * @param itemStack The ItemStackRep that is used for repairing
     * @param human The Human repairing the OilItemStack
     * @param oldDurability
     * @return how many of these items should be used
     */
    default int prepareRepairAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human, int usedItems, int oldDurability) {
        return usedItems;
    }

    /**
     *
     * @param itemStack The ItemStackRep that is used for repairing
     * @param human The Human repairing the OilItemStack
     */
    default void repairAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human) {}

    /**
     * Should modify the OilItemStack in a way that it looks visually like the ItemStackRep after the combination.
     * This should only include lightweight and fast algorithms. This method should not modify itemStack in any way.
     * This method is called when a player puts everything needed into the anvil and it is not certain if a combination is really executed.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    default int prepareCombineAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human, int cost) {return cost;}

    /**
     * This method is called when a player combines the items in an anvil. It should do anything that could not be done in prepareCombineAnvil.
     * @param itemStack The itemstack that is combined with the OilItemStack
     * @param human The Human combining the OilItemStack with the ItemStack
     */
    default void combineAnvil(OilItemStack oilItemStack, ItemStackRep itemStack, EntityHumanRep human) {}

    /**
     * Override this method to allow enchanting
     * @param enchantment the enchantment that is checked
     * @return returns whether the enchantment can be applied to this item
     */ //TODO: replace boolean with enum like class
    default boolean canEnchantSpecial(EnchantmentRep enchantment, boolean anvil) {return false;}

    default ItemImplementationProvider getImplementationProvider() {
        if (this instanceof IItemSpecialised) throw new NotImplementedException("do not call IItemGeneric.getImplementationProvider from specialised subclass, either provide ImplementationProvider yourself or call specialising interface");
        return  ItemImplementationProvider.CUSTOM.getValue();
    }

    /**
     * This is called before the OilItemStack is completely initialised. i.e. after the constructor, used to add for example data tags
     */
    default void initStack(OilItemStack stack){}
}
