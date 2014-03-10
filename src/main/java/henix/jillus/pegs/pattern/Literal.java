package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class Literal extends PegPattern {

	public final String str;

	public Literal(String str) {
		if (str == null) {
			throw new IllegalArgumentException("new Literal(null)");
		}
		this.str = str;
	}
}
