package org.oilmod.api.registry;

import org.oilmod.api.util.IKeyed;
import org.oilmod.api.util.OilKey;

public interface IKeySettable extends IKeyed {
    void setOilKey(OilKey key);
}
