package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class AtLeast extends PegPattern {

	public final int n;
	public final PegPattern e;

	public AtLeast(int n, PegPattern e) {
		this.n = n;
		this.e = e;
	}
}
