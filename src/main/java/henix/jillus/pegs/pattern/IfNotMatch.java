package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class IfNotMatch extends PegPattern {

	public final PegPattern cond;
	public final PegPattern e;

	public IfNotMatch(PegPattern cond, PegPattern e) {
		this.cond = cond;
		this.e = e;
	}
}
