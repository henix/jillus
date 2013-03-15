package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class OrderChoice extends PegPattern {

	public final PegPattern[] patts;

	public OrderChoice(PegPattern... patts) {
		this.patts = patts;
	}
}
