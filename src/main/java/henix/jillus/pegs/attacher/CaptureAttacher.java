package henix.jillus.pegs.attacher;

import henix.jillus.Capturer;
import henix.jillus.FieldSetter;

/**
 * Connect a inner capture to a outer compound capture
 *
 * @param <T> outer type
 * @param <V> inner type
 */
public class CaptureAttacher<T, V> extends Attacher<T> {

	public final FieldSetter<T, V> fieldSetter;
	public final Capturer<? extends V> c;

	public CaptureAttacher(FieldSetter<T, V> fieldSetter, Capturer<? extends V> c) {
		this.fieldSetter = fieldSetter;
		this.c = c;
	}
}
