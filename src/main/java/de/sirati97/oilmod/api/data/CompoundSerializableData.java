package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;
import de.sirati97.oilmod.api.config.CompoundSerializable;

/**
 * Created by sirati97 on 12.02.2016.
 */
public class CompoundSerializableData<T extends CompoundSerializable> extends CompoundData<T> {
    private final ObjectFactory<T> factory;
    private final boolean recreateOnLoad;
    private boolean initialized = false;

    public CompoundSerializableData(String name, DataParent dataParent, ObjectFactory<T> factory, boolean recreateOnLoad) {
        super(name, dataParent);
        this.factory = factory;
        this.recreateOnLoad = recreateOnLoad;
    }

    @Override
    public T getData() {
        if (initialized) {
            return super.getData();
        } else {
            T result = super.getData();
            if (result==null) {
                setData(result=factory.create());
                onCreated();
            }
            initialized = true;
            return result;
        }
        
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
            onCreated();
        } else {
            result = getData();
        }
        result.load(compound);
        return result;
    }

    protected void onCreated(){}

    public boolean recreateOnLoad() {
        return recreateOnLoad;
    }

    public ObjectFactory<T> getFactory() {
        return factory;
    }

    @Override
    public void onCloned(IData<T> original) {
        setData((T)original.getData().cloneIfCloneable());
    }
}
