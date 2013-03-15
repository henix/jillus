package henix.jillus.utils;

import henix.jillus.ValueCreator;

public class Identical implements ValueCreator<String> {

	public static final Identical instance = new Identical();

	public String create(String capture) {
		return capture;
	}
}
