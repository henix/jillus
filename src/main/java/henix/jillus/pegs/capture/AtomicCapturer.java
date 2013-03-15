package henix.jillus.pegs.capture;

import henix.jillus.Capturer;
import henix.jillus.PegPattern;
import henix.jillus.ValueCreator;

public class AtomicCapturer<T> extends Capturer<T> {

	public final ValueCreator<T> valueCreator;
	public final PegPattern e;

	public AtomicCapturer(ValueCreator<T> valueCreator, PegPattern e) {
		this.valueCreator = valueCreator;
		this.e = e;
	}
}
