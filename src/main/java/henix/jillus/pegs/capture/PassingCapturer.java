package henix.jillus.pegs.capture;

import henix.jillus.Capturer;
import henix.jillus.PegPattern;

/**
 * Pass a capture from a sequence of patterns
 */
public class PassingCapturer<T> extends Capturer<T> {

	public final PegPattern before;
	public final Capturer<? extends T> e;
	public final PegPattern after;

	public PassingCapturer(PegPattern before, Capturer<? extends T> e, PegPattern after) {
		this.before = before;
		this.e = e;
		this.after = after;
	}

	public PassingCapturer(PegPattern before, Capturer<? extends T> e) {
		this(before, e, null);
	}

	public PassingCapturer(Capturer<? extends T> e, PegPattern after) {
		this(null, e, after);
	}
}
