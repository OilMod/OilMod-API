package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class IntegerArrayData implements IData<int[]> {
    private int[] data;
    private String name;
    private DataParent dataParent;

    public IntegerArrayData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setIntArray(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getIntArray(name);
    }

    public String getName() {
        return name;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
