package org.oilmod.api.test.providers.items;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.items.type.ItemImplementationProvider;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.rep.item.ItemStateRep;
import org.oilmod.api.rep.providers.ItemProvider;

public class TestIIPHelper extends ItemImplementationProvider.Helper<TestIIPHelper> {
    @Override
    protected ItemImplementationProvider getProvider(ItemImplementationProvider.TypeEnum itemType) {
        return new ItemImplementationProvider(itemType) {
            @Override
            public ItemRep implement(OilItem item) {
                return new TestItemRep(item);
            }
        };
    }

    private static class TestItemRep implements ItemRep {
        private OilItem item;

        public TestItemRep(OilItem item) {

            this.item = item;
        }

        @Override
        public ItemStateRep getStandardState() {
            return null;
        }

        @Override
        public int getMaxDurability() {
            return 0;
        }

        @Override
        public boolean isSimilar(ItemProvider item) {
            return item.getProvidedItem() instanceof TestItemRep && ((TestItemRep) item.getProvidedItem()).item == this.item;
        }
    }
}
