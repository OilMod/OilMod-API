package org.oilmod.api.UI;

import org.apache.commons.lang3.Validate;
import org.oilmod.api.data.DataParent;
import org.oilmod.api.data.IData;
import org.oilmod.api.registry.IKeySettable;
import org.oilmod.api.util.OilKey;

public abstract class UIFactory<TContext> implements IKeySettable {
    public abstract UI<TContext> create(TContext context);
    public abstract DataParent getDataParent(TContext context);
    public abstract DataParent createDataParent();
    public abstract TContext getContext(DataParent data);

    private OilKey key;
    @Override
    public void setOilKey(OilKey key) {
        Validate.isTrue(this.key == null, "Cannot manually set key! This is done by the registry!");
        this.key = key;
    }

    @Override
    public OilKey getOilKey() {
        return key;
    }
}
