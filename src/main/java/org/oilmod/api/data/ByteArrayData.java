package org.oilmod.api.data;

import org.oilmod.api.config.Compound;

/**
 * Created by sirati97 on 17.01.2016.
 */
public class ByteArrayData extends IClonableDataBase<byte[]> {
    private byte[] data;

    public ByteArrayData(String name, DataParent dataParent) {
        super(name, dataParent);
    }

    public void saveTo(Compound parent, String name) {
        parent.setByteArray(name, data);
    }

    public void loadFrom(Compound parent, String name) {
        this.data = parent.getByteArray(name);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void onCloned(IData<byte[]> original) {
        this.setData(original.getData().clone());
    }
}
