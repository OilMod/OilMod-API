package org.oilmod.api.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Collections;
import java.util.Map;

import static org.oilmod.util.Strings.simpleName;

public class DataParentImpl implements IDataParent {
    private final Map<String,IData<?>> registeredIData = new Object2ObjectOpenHashMap<>();
    private final Map<String,IData<?>> readonly_registeredIData = Collections.unmodifiableMap(registeredIData);
    private boolean initiated = false;

    /**
     * Internal - should not be called by user code. Is called AFTER nms representation is initialised.
     * IData must be added before this method is called!
     */
    public final void init() {
        if (initiated) {
            throw new IllegalStateException(simpleName(getClass()) + " is has already been initiated");
        }
        onInit();
        initiated = true;
    }

    protected void onInit() {      }

    /**
     * Attaches an iData to the ItemStack. It value will be saved to nbt. You normally do not need to call this manually
     * @param iData the datatag that should be added
     */
    @Override
    public void registerIData(IData<?> iData) {
        if (initiated)throw new IllegalStateException("Cannot register new IData after initialisation! IData would not be able to load data and always have standard value!");
        registeredIData.put(iData.getName(),iData);
    }

    /**
     *
     * @return returns all attached iData mapped with there NBT-key
     */
    @Override
    public Map<String, IData<?>> getRegisteredIData() {
        return readonly_registeredIData;
    }
}
