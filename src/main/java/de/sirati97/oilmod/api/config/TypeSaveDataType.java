package de.sirati97.oilmod.api.config;

import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Created by sirati97 on 10.02.2016.
 */
public enum TypeSaveDataType {
    NBTEnd(0),Byte(1),Short(2),Int(3),Long(4),Float(5),Double(6),ByteArray(7),String(8),NBTList(10),Subsection(10),IntArray(11),Undefined(-1);

    private int nbtId;
    private static TIntObjectHashMap<TypeSaveDataType> nbtMap = new TIntObjectHashMap<>(14);

    TypeSaveDataType(int nbtId) {
        this.nbtId = nbtId;
        register(this);
    }

    public int getNbtId() {
        return nbtId;
    }

    private static void register(TypeSaveDataType type) {
        nbtMap.put(type.getNbtId(), type);
    }

    public static TypeSaveDataType getByNbtId(int id) {
        return nbtMap.get(id);
    }

}
