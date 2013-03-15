package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class Optional extends PegPattern {

	public final PegPattern e;

	public Optional(PegPattern e) {
		this.e = e;
	}
}
