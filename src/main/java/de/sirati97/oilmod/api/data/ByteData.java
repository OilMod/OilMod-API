package de.sirati97.oilmod.api.data;

import de.sirati97.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class ByteData implements IData<Byte> {
    private byte data;
    private String name;
    private DataParent dataParent;

    public ByteData(String name, DataParent dataParent) {
        this.name = name;
        this.dataParent = dataParent;
        dataParent.registerIData(this);
    }

    public void saveTo(Compound parent, String name) {
        parent.setByte(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getByte(name);
    }

    public String getName() {
        return name;
    }

    public Byte getData() {
        return data;
    }

    public void setData(Byte data) {
        this.data = data;
    }

    public DataParent getParent() {
        return dataParent;
    }
}
