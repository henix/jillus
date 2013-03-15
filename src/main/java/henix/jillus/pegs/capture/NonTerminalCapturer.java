package henix.jillus.pegs.capture;

import henix.jillus.Capturer;

public class NonTerminalCapturer<T> extends Capturer<T> {

	public Capturer<? extends T> actual;

	public void set(Capturer<? extends T> actual) {
		this.actual = actual;
	}
}
