package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class IntegerArrayData extends IClonableDataBase<int[]> {
    private int[] data;

    public IntegerArrayData(String name, DataParent dataParent) {
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
    }

    @Override
    public void onCloned(IData<int[]> original) {

    }
}
