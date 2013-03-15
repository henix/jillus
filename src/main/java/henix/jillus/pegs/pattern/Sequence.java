package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class Sequence extends PegPattern {

	public final PegPattern[] patts;

	public Sequence(PegPattern... patts) {
		this.patts = patts;
	}
}
