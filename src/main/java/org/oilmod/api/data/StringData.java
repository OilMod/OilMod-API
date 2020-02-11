package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class StringData extends IPrimaryDataBase<String> {
    private String data;

    public StringData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setString(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getString(name);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        getParent().markUpdated();
    }
}
