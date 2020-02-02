package org.oilmod.api.data;

/**
 * Created by sirati97 on 12.02.2016.
 */
public abstract class IPrimaryDataBase<T> extends IDataBase<T> {

    public IPrimaryDataBase(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    @Override
    public void onCloned(IData<T> original) {
        this.setData(original.getData());
    }
}
