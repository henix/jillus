package henix.jillus.pegs.capture;

import henix.jillus.Capturer;

/**
 * Implement one-of datatype
 */
public class OrderChoiceCapturer<T> extends Capturer<T> {

	public final Capturer<? extends T>[] alternatives;

	public OrderChoiceCapturer(Capturer<? extends T>... alternatives) {
		this.alternatives = alternatives;
	}
}
