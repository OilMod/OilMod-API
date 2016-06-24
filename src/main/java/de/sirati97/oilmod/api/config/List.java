package de.sirati97.oilmod.api.config;

public interface List<Type> {
    void append(Type type);
    void set(int index, Type type);
    Type get(int index);
    Type remove(int index);
	int size();
    DataType getType();
    Class<Type> getJavaType();
}
