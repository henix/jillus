package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class CharInSet extends PegPattern {

	public final String str;

	public CharInSet(String str) {
		if (str == null) {
			throw new IllegalArgumentException("new CharInSet(null)");
		}
		this.str = str;
	}
}
