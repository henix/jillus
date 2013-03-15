package henix.jillus.pegs.capture;

import henix.jillus.Capturer;
import henix.jillus.RecordCreator;
import henix.jillus.pegs.attacher.Attacher;

/**
 * Accumulate inner captured values into a compound value.
 *
 * Implement many-of datatype
 */
public class CompoundCapturer<T> extends Capturer<T> {

	public final RecordCreator<T> recordCreator;
	public final Attacher<? super T> a;

	public CompoundCapturer(RecordCreator<T> recordCreator, Attacher<? super T> a) {
		this.recordCreator = recordCreator;
		this.a = a;
	}
}
