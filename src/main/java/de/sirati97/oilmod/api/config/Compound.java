package de.sirati97.oilmod.api.config;


public interface Compound extends CompoundCreator {
	void set(String paramString, Compound paramCompound);
	void setList(String paramString, List paramList);
	
	void setByte(String paramString, byte paramByte);
	void setShort(String paramString, short paramShort);
	void setInt(String paramString, int paramInt);
	void setLong(String paramString, long paramLong);
	void setFloat(String paramString, float paramFloat);
	void setDouble(String paramString, double paramDouble);
	void setString(String paramString1, String paramString2);
	void setByteArray(String paramString, byte[] paramArrayOfByte);
	void setIntArray(String paramString, int[] paramArrayOfInt);
	void setBoolean(String paramString, boolean paramBoolean);
	void setNBT(String paramString, Object paramNBTTag);
	byte getByte(String paramString);
	short getShort(String paramString);
	int getInt(String paramString);
	long getLong(String paramString);
	float getFloat(String paramString);
	double getDouble(String paramString);
	String getString(String paramString);
	byte[] getByteArray(String paramString);
	int[] getIntArray(String paramString);
	Compound getCompound(String paramString);
    <Type> List<Type> getList(String paramString);
	boolean getBoolean(String paramString);
	Object getRaw(String paramString);
    boolean containsKey(String paramString);
    boolean containsKey(String paramString, DataType type);
    Object nbtClone();
    DataType getType(String key);
}
