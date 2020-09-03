package org.oilmod.api.test.providers.block;

import org.oilmod.api.blocks.BlockType;
import org.oilmod.api.blocks.MapColor;
import org.oilmod.api.blocks.PistonReaction;
import org.oilmod.api.blocks.nms.NMSBlockType;
import org.oilmod.api.rep.block.BlockStateRep;

public class TestBlockTypeHelper extends BlockType.Helper<TestBlockTypeHelper> {
    @Override
    protected BlockType getVanillaBlockType(BlockType.BlockTypeEnum blockType) {
        BlockType b = get(blockType);
        if (b == null) {
            b = new BlockType(new NMSBlockType() {},blockType) {
                @Override
                public boolean isLiquid() {
                    return false;
                }

                @Override
                public boolean isBuildable() {
                    return false;
                }

                @Override
                public boolean isOpaque() {
                    return false;
                }

                @Override
                public boolean isSolid() {
                    return false;
                }

                @Override
                public boolean isFlammable() {
                    return false;
                }

                @Override
                public boolean isReplaceable() {
                    return false;
                }

                @Override
                public boolean isAlwaysDestroyable() {
                    return false;
                }

                @Override
                public boolean breakablePickaxe(BlockStateRep material) {
                    return false;
                }

                @Override
                public boolean breakableAxe(BlockStateRep material) {
                    return false;
                }

                @Override
                public boolean breakableShovel(BlockStateRep material) {
                    return false;
                }

                @Override
                public boolean breakableShears(BlockStateRep material) {
                    return false;
                }

                @Override
                public boolean breakableBlade(BlockStateRep material) {
                    return false;
                }

                @Override
                public PistonReaction getPistonReaction() {
                    return null;
                }

                @Override
                public MapColor getColor() {
                    return null;
                }
            };
        }
        return b;
    }

    @Override
    protected NMSBlockType registerCustom(BlockType blockType) {
            return new NMSBlockType() {};
    }
}
