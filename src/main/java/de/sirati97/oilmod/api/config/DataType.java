package de.sirati97.oilmod.api.config;

import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Created by sirati97 on 10.02.2016.
 */
public enum DataType {
    NBTEnd(0, void.class),
    Byte(1, Byte.class),
    Short(2, Short.class),
    Int(3, Integer.class),
    Long(4, Long.class),
    Float(5, Float.class),
    Double(6, Double.class),
    ByteArray(7, (new byte[0]).getClass()),
    String(8, String.class),
    NBTList(10, DataList.class),
    Subsection(10, Compound.class),
    IntArray(11, (new byte[0]).getClass()),
    Undefined(-1, void.class);

    private final int nbtId;
    private final Class<?> javaClass;
    private static TIntObjectHashMap<DataType> nbtMap;

    DataType(int nbtId, Class<?> javaClass) {
        this.nbtId = nbtId;
        this.javaClass = javaClass;
        register(this);
    }

    public int getNbtId() {
        return nbtId;
    }

    private static void register( DataType type) {
        if (nbtMap == null) {
            nbtMap = new TIntObjectHashMap<>(14);
        }
        nbtMap.put(type.getNbtId(), type);
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }

    public static DataType getByNbtId(int id) {
        return nbtMap.get(id);
    }

}
