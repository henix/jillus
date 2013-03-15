package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class Literal extends PegPattern {

	public final String str;
	public final int len;

	public Literal(String str) {
		if (str == null) {
			throw new IllegalArgumentException("new Literal(null)");
		}
		this.str = str;
		this.len = str.length();
	}
}
