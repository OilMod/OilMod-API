package org.oilmod.api.test.providers.rep.inventory;

import org.oilmod.api.rep.inventory.InventoryFactory;
import org.oilmod.api.rep.inventory.InventoryRep;
import org.oilmod.api.rep.inventory.InventoryViewImpl;
import org.oilmod.api.rep.inventory.MirrorSizedInventoryRep;
import org.oilmod.api.rep.itemstack.ItemStackRep;

public class TestInventoryFactory extends InventoryFactory<TestInventoryFactory> {
    @Override
    public InventoryRep createHeadlessInventory(int size) {
        return InventoryRep.EMPTY;
    }

    @Override
    public InventoryRep createHeadlessInventory(int height, int width) {
        return InventoryRep.EMPTY;
    }


    @Override
    public MirrorSizedInventoryRep createFixedSizeMirrorer1d(InventoryRep inv) {
        int size = inv.getSize();
        return new MirrorSizedInventoryRep() {
            @Override
            public InventoryRep getMirroree() {
                return inv;
            }

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
            public boolean isEmpty(int slot) {
                return true;
            }

            @Override
            public int getStack(int slot) {
                return 0;
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
    public MirrorSizedInventoryRep createFixedSizeMirrorer2d(InventoryRep inv) {
        int height = inv.getHeight();
        int width = inv.getWidth();

        return new MirrorSizedInventoryRep() {
            @Override
            public InventoryRep getMirroree() {
                return inv;
            }

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
            public boolean isEmpty(int slot) {
                return true;
            }

            @Override
            public int getStack(int slot) {
                return 0;
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
