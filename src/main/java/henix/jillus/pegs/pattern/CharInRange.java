package henix.jillus.pegs.pattern;

import henix.jillus.PegPattern;

public class CharInRange extends PegPattern {

	public final char from;
	public final char to;

	public CharInRange(char from, char to) {
		this.from = from;
		this.to = to;
	}
}
