package org.oilmod.api.test.providers.block;

import org.oilmod.api.blocks.OilBlock;
import org.oilmod.api.blocks.type.BlockImplementationProvider;
import org.oilmod.api.rep.block.BlockRep;
import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.item.BlockItemRep;

public class TestBIPHelper extends BlockImplementationProvider.Helper<TestBIPHelper> {
    @Override
    protected BlockImplementationProvider getProvider(BlockImplementationProvider.TypeEnum blockType) {
        return new BlockImplementationProvider(blockType) {
            @Override
            public BlockRep implement(OilBlock block) {
                return new MyBlockRep();
            }
        };
    }

    private static class MyBlockRep implements BlockRep {
        @Override
        public boolean hasItem() {
            return false;
        }

        @Override
        public BlockItemRep getItem() {
            return null;
        }

        @Override
        public BlockStateRep getStandardState() {
            return null;
        }
    }
}
