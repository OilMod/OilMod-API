package org.oilmod.api.blocks;

import org.oilmod.api.blocks.type.IBlockGeneric;
import org.oilmod.api.registry.KeySettableBase;
import org.oilmod.api.rep.item.ItemStateRep;
import org.oilmod.api.rep.providers.BlockStateProvider;
import org.oilmod.api.stateable.IState;

import java.util.function.Function;

public abstract class OilBlock extends KeySettableBase implements IBlockGeneric {
    private Object nmsBlock;
    private BlockStateProvider vanillaBlock;
    private String displayName;
    private Function<IState, ItemStateRep> factory;

    protected OilBlock(BlockStateProvider vanillaBlock, String displayName) {
        this.vanillaBlock = vanillaBlock;
        this.displayName = displayName;
    }

    public Object getNmsBlock() {
        return nmsBlock;
    }

    public void setNmsBlock(Object nmsBlock) {
        this.nmsBlock = nmsBlock;
    }

    public BlockStateProvider getVanillaBlock() {
        return vanillaBlock;
    }

    public String getDisplayName() {
        return displayName;
    }


    @Override
    public void setItemFactory(Function<IState, ItemStateRep> factory) {
        this.factory = factory;
    }

    @Override
    public Function<IState, ItemStateRep> getItemFactory() {
        return factory;
    }

}
