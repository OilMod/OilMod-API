package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class BooleanData extends IPrimaryDataBase<Boolean> {
    private boolean data;

    public BooleanData(String name, DataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setBoolean(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getBoolean(name);
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

}
