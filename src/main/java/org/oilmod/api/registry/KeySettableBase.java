package org.oilmod.api.registry;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.util.OilKey;

public abstract class KeySettableBase implements IKeySettable {
    private OilKey key;

    @Override
    public void setOilKey(OilKey key) {
        Validate.isTrue(this.key == null, "Cannot manually set key! This is done by the registry!");
        this.key = key;
    }


    /**
     *
     * @return returns the mod unique key of this item
     */
    public final OilKey getOilKey() {
        return key;
    }


}
