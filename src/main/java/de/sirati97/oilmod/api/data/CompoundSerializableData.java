package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;
import de.sirati97.oilmod.api.config.CompoundSerializable;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class CompoundSerializableData<T extends CompoundSerializable> extends CompoundData<T> {
    private final ObjectFactory<T> factory;
    private final boolean recreateOnLoad;

    public CompoundSerializableData(String name, DataParent dataParent, ObjectFactory<T> factory, boolean recreateOnLoad) {
        super(name, dataParent);
        this.factory = factory;
        this.recreateOnLoad = recreateOnLoad;
        setData(factory.create());
    }

    @Override
    public void save(Compound compound) {
        getData().save(compound);
    }

    @Override
    public T load(Compound compound) {
        T result;
        if (recreateOnLoad()) {
            result = factory.create();
        } else {
            result = getData();
        }
        result.load(compound);
        return result;
    }

    public boolean recreateOnLoad() {
        return recreateOnLoad;
    }

    public ObjectFactory<T> getFactory() {
        return factory;
    }
}
