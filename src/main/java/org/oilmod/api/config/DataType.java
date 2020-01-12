package org.oilmod.api.config;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * Created by sirati97 on 10.02.2016.
 */
public enum DataType {
    NBTEnd(0, void.class, null),
    Byte(1, Byte.class, (byte)0),
    Short(2, Short.class, (short)0),
    Int(3, Integer.class, 0),
    Long(4, Long.class, (long)0),
    Float(5, Float.class, (float)0),
    Double(6, Double.class, (double)0),
    ByteArray(7, byte[].class, new byte[0]),
    String(8, String.class, ""),
    List(9, DataList.class, null),
    Subsection(10, Compound.class, null),
    IntArray(11, int[].class, new int[0]),
    LongArray(12, long[].class, new long[0]),
    Undefined(-1, void.class, null),
    Empty(-1, void.class, null);

    private final int nbtId;
    private final Class<?> javaClass;
    private final Object standardValue;
    private static Int2ObjectMap<DataType> nbtMap;

    DataType(int nbtId, Class<?> javaClass, Object standardValue) {
        this.nbtId = nbtId;
        this.javaClass = javaClass;
        this.standardValue = standardValue;
        register(this);
    }

    public int getNbtId() {
        return nbtId;
    }

    private static void register( DataType type) {
        if (nbtMap == null) {
            nbtMap = new Int2ObjectOpenHashMap<>(14);
        }
        nbtMap.put(type.getNbtId(), type);
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }

    public Object getStandardValue() {
        return standardValue;
    }

    public static DataType getByNbtId(int id) {
        return nbtMap.get(id);
    }

}
