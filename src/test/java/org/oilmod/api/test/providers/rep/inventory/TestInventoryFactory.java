package org.oilmod.api.test.providers.rep.inventory;

import org.oilmod.api.rep.inventory.InventoryFactory;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.rep.inventory.InventoryViewImpl;
import org.oilmod.api.rep.itemstack.ItemStackRep;

public class TestInventoryFactory extends InventoryFactory<TestInventoryFactory> {
    @Override
    public InventoryRep createHeadlessInventory(int size) {
        return new InventoryRep() {
            @Override
            public int getWidth() {
                return size;
            }

            @Override
            public int getHeight() {
                return 1;
            }

            @Override
            public ItemStackRep getStored(int slot) {
                return null;
            }

            @Override
            public void setStored(int slot, ItemStackRep stack) {

            }

            @Override
            public boolean isNative() {
                return false;
            }

            @Override
            public int getMaxStack(int slot) {
                return 0;
            }
        };
    }

    @Override
    public InventoryRep createHeadlessInventory(int height, int width) {
        return new InventoryRep() {
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public ItemStackRep getStored(int slot) {
                return null;
            }

            @Override
            public void setStored(int slot, ItemStackRep stack) {

            }

            @Override
            public boolean isNative() {
                return false;
            }

            @Override
            public int getMaxStack(int slot) {
                return 0;
            }
        };
    }
}
