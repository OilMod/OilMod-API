package org.oilmod.api.items.type;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.oilmod.api.blocks.IBlockState;
import org.oilmod.api.items.EnchantmentType;
import org.oilmod.api.items.ItemInteractionResult;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.util.InteractionResult;

public interface IItemGeneric {

    default boolean onEntityHit(OilItemStack stack, LivingEntity target, LivingEntity attacker) {return true;}

    default boolean onBlockDestroyed(OilItemStack stack, IBlockState blockState, Location pos, LivingEntity entityLiving) {return true;}


    default InteractionResult onItemUseOnBlock(OilItemStack stack, HumanEntity human, Location pos, boolean offhand, BlockFace facing, float hitX, float hitY, float hitZ) {return InteractionResult.NONE;}

    default ItemInteractionResult onItemRightClick(OilItemStack stack, World world, HumanEntity human, boolean offhand) {return new ItemInteractionResult(InteractionResult.NONE, stack.asBukkitItemStack());}

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
}
