package henix.jillus.utils;

import henix.jillus.ValueCreator;

public class ToFixedValue<T> implements ValueCreator<T> {

	private final T value;

	public ToFixedValue(T value) {
		this.value = value;
	}

	public T create(String capture) {
		return value;
	}
}
