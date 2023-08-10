package org.oilmod.api.unification.item;


import org.oilmod.api.rep.providers.ItemProvider;
import org.oilmod.api.unification.material.IUniMaterial;
import org.oilmod.api.util.DeferredProvider;

public class UniItemBuilder extends ItemBuilderBase<UniItem, UniItemBuilder> {

    private final DeferredProvider<ItemProvider> vanillaIcon;

    public UniItemBuilder(ItemProvider.StdDeferred vanillaIcon) {
        this(() -> vanillaIcon);
    }
    public UniItemBuilder(DeferredProvider<ItemProvider> vanillaIcon) {
        this.vanillaIcon = vanillaIcon;
    }

    @Override
    protected UniItem implement(IUniMaterial type, IUniMaterial base) {
        return new UniItem(vanillaIcon.get(), "TODO add display name", base);
    }
}
