package henix.jillus.utils;

import java.util.List;

import henix.jillus.FieldSetter;

public class ListAppender<T> implements FieldSetter<List<T>, T> {

	public void setValue(List<T> obj, T value) {
		obj.add(value);
	}
}
