package henix.jillus.pegs.attacher;

import henix.jillus.PegPattern;

public class NothingAttacher extends Attacher<Object> {

	public final PegPattern e;

	public NothingAttacher(PegPattern e) {
		this.e = e;
	}
}
