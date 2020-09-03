package org.oilmod.api.test.providers.items.tools;

import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.items.OilItemStack;
import org.oilmod.api.items.type.IToolBlockBreaking;
import org.oilmod.api.items.type.ItemImplementationProvider;
import org.oilmod.api.items.type.TBBType;
import org.oilmod.api.rep.block.BlockFaceRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.entity.EntityLivingRep;
import org.oilmod.api.rep.world.LocationBlockRep;
import org.oilmod.api.util.InteractionResult;

public class TestTBBHelper extends TBBType.Helper<TestTBBHelper> {
    @Override
    protected TBBType getProvider(TBBType.TBBEnum itemType) {
        return new TBBType(itemType) {

            @Override
            protected boolean canHarvestBlock(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, BlockType blockType) {
                return false;
            }

            @Override
            protected float getDestroySpeed(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, BlockType blockType) {
                return 0;
            }

            @Override
            protected boolean onEntityHit(IToolBlockBreaking item, OilItemStack stack, EntityLivingRep target, EntityLivingRep attacker) {
                return false;
            }

            @Override
            protected boolean onBlockDestroyed(IToolBlockBreaking item, OilItemStack stack, BlockStateRep blockState, LocationBlockRep location, EntityLivingRep entityLiving) {
                return false;
            }

            @Override
            protected InteractionResult onItemUseOnBlock(IToolBlockBreaking item, OilItemStack stack, EntityLivingRep humanEntity, LocationBlockRep pos, boolean offhand, BlockFaceRep facing, float hitX, float hitY, float hitZ) {
                return null;
            }

            @Override
            protected ItemImplementationProvider getImplementationProvider() {
                return null;
            }
        };
    }
}
