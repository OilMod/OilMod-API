package org.oilmod.api.unification.item;

import org.oilmod.api.items.OilItem;
import org.oilmod.api.rep.item.ItemRep;
import org.oilmod.api.unification.IExpression;
import org.oilmod.api.unification.material.UniMaterialWrapper;
import org.oilmod.api.util.OilKey;

public class UniItem extends OilItem implements IExpression {
    private final UniMaterialWrapper uniMaterial;

    /**
     * @param key         Mod unique key that identifies the item
     * @param vanillaItem The Vanilla Material that is shown to the client
     * @param displayName displayed displayName of the item
     * @param uniMaterial
     */
    public UniItem(OilKey key, ItemRep vanillaItem, String displayName, UniMaterialWrapper uniMaterial) {
        super(key, vanillaItem, displayName);
        this.uniMaterial = uniMaterial;
    }

    @Override
    public UniMaterialWrapper getUniMaterial() {
        return uniMaterial;
    }
}
