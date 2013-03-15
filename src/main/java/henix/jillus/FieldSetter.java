package henix.jillus;

public interface FieldSetter<T, V> {

	/**
	 * Set one of obj's field to value
	 */
	void setValue(T obj, V value);
}
