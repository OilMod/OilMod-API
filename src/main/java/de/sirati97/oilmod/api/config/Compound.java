package de.sirati97.oilmod.api.config;


public interface Compound extends CompoundCreator, Iterable<DataKeyedEntry> {
	void set(String key, Compound paramCompound);
	void setList(String key, DataList paramDataList);
	
	void setByte(String key, byte paramByte);
	void setShort(String key, short paramShort);
	void setInt(String key, int paramInt);
	void setLong(String key, long paramLong);
	void setFloat(String key, float paramFloat);
	void setDouble(String key, double paramDouble);
	void setString(String paramString1, String paramString2);
	void setByteArray(String key, byte[] paramArrayOfByte);
	void setIntArray(String key, int[] paramArrayOfInt);
	void setBoolean(String key, boolean paramBoolean);
	void setNBT(String key, Object paramNBTTag);
    void set(String key, Object param, DataType type);
    void set(DataKeyedEntry dataKeyedEntry);
    byte getByte(String key);
	short getShort(String key);
	int getInt(String key);
	long getLong(String key);
	float getFloat(String key);
	double getDouble(String key);
	String getString(String key);
	byte[] getByteArray(String key);
	int[] getIntArray(String key);
	Compound getCompound(String key);
    <Type> DataList<Type> getList(String key);
	boolean getBoolean(String key);
	Object getRaw(String key);
    DataKeyedEntry get(String key);
    boolean containsKey(String key);
    boolean containsKey(String key, DataType type);
    Object nbtClone();
    DataType getType(String key);
}
