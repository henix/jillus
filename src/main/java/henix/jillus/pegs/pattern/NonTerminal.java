package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class NonTerminal extends PegPattern {

	public PegPattern actual;

	public void set(PegPattern actual) {
		this.actual = actual;
	}
}
