package henix.jillus.utils;

import henix.jillus.RecordCreator;

public class ReflectClassMaker<T> implements RecordCreator<T> {

	private final Class<T> klass;

	public ReflectClassMaker(Class<T> klass) {
		this.klass = klass;
	}

	public T create() {
		try {
			return klass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
