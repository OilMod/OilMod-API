package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class ByteData extends IPrimaryDataBase<Byte> {
    private byte data;

    public ByteData(String name, IDataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setByte(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getByte(name);
    }

    public Byte getData() {
        return data;
    }

    public void setData(Byte data) {
        this.data = data;
    }
}
