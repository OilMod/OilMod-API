package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class IntegerArrayData extends IClonableDataBase<int[]> {
    private int[] data;

    public IntegerArrayData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setIntArray(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getIntArray(name);
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
        getParent().markUpdated();
    }

    @Override
    public void onCloned(IData<int[]> original) {

    }
}
