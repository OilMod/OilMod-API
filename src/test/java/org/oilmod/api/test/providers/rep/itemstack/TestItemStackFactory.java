package org.oilmod.api.test.providers.rep.itemstack;

import org.oilmod.api.rep.block.BlockStateRep;
import org.oilmod.api.rep.item.BlockItemRep;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.item.ItemStateRep;
import org.oilmod.api.rep.itemstack.ItemStackFactory;
import org.oilmod.api.rep.itemstack.ItemStackRep;
import org.oilmod.api.rep.itemstack.state.ItemStackStateRep;
import org.oilmod.api.rep.providers.ItemStackStateProvider;
import org.oilmod.api.rep.stdimpl.itemstack.state.ItemStackStateImpl;

public class TestItemStackFactory extends ItemStackFactory<TestItemStackFactory> {
    @Override
    public ItemStackRep create(ItemRep item, ItemStackStateRep state, int amount) {
        return new ItemStackRep() {
            @Override
            public ItemRep getItem() {
                return item;
            }

            @Override
            public ItemStackStateRep getItemStackState() {
                return state;
            }

            @Override
            public int getAmount() {
                return 0;
            }

            @Override
            public void setAmount(int amount) {

            }

            @Override
            public int getMaxStackSize() {
                return 0;
            }

            @Override
            public boolean isSimilar(ItemStackStateProvider stack) {
                return false;
            }

            @Override
            public ItemStackRep copy() {
                return this;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
    }

    @Override
    public ItemStackRep empty() {
        return new ItemStackRep() {
            @Override
            public ItemRep getItem() {
                return null;
            }

            @Override
            public ItemStackStateRep getItemStackState() {
                return null;
            }

            @Override
            public int getAmount() {
                return 0;
            }

            @Override
            public void setAmount(int amount) {

            }

            @Override
            public int getMaxStackSize() {
                return 0;
            }

            @Override
            public boolean isSimilar(ItemStackStateProvider stack) {
                return false;
            }

            @Override
            public ItemStackRep copy() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }
        };
    }

    @Override
    public ItemStackStateRep createStackState(ItemRep item, ItemStateRep itemState) {
        return new ItemStackStateImpl(itemState);
    }

    @Override
    public ItemStackStateRep createStackState(BlockItemRep item, BlockStateRep itemState) {
        return new ItemStackStateImpl(item.getStandardState());
    }
}
