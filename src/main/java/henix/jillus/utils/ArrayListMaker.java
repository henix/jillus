package henix.jillus.utils;

import java.util.ArrayList;
import java.util.List;

import henix.jillus.RecordCreator;

public class ArrayListMaker<T> implements RecordCreator<List<T>> {

	public List<T> create() {
		return new ArrayList<T>();
	}
}
