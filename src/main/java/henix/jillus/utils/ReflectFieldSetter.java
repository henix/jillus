package henix.jillus.utils;

import java.lang.reflect.Field;

import henix.jillus.FieldSetter;

public class ReflectFieldSetter<T, V> implements FieldSetter<T, V> {

	private final Class<T> tclass;
	private final String fieldName;

	public ReflectFieldSetter(Class<T> tclass, Class<V> vclass, String fieldName) {
		this.tclass = tclass;
		this.fieldName = fieldName;
	}

	public void setValue(T obj, V value) {
		try {
			final Field field = tclass.getField(fieldName);
			field.set(obj, value);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
