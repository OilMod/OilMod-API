package de.sirati97.oilmod.api.data;

import java.util.Map;

/**
 * Created by sirati97 on 17.01.2016.
 */
public interface DataParent {
    void registerIData(IData<?> iData);
    Map<String, IData<?>> getRegisteredIData();
}
