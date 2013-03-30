package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class NotPredict extends PegPattern {

	public final PegPattern e;

	public NotPredict(PegPattern e) {
		this.e = e;
	}
}
