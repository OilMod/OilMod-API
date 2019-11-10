package org.oilmod.api.items.type;

import org.apache.commons.lang3.NotImplementedException;

/**
 * for internal use and instance of checking online. do not extend anywhere but in specialisation interfaces.
 * extending interfaced need to provide specialised implementation provider
 */
public interface IItemSpecialised extends IItemGeneric {


    @Override
    default ImplementationProvider getImplementationProvider() {
        throw new NotImplementedException("Do not implement anywhere but in specialisation interfaces. Extending interfaces need to provide specialised implementation provider");
    }
}
