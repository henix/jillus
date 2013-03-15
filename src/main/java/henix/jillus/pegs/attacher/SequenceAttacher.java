package henix.jillus.pegs.attacher;

public class SequenceAttacher<T> extends Attacher<T> {

	public final Attacher<? super T>[] attachers;

	public SequenceAttacher(Attacher<? super T>... attachers) {
		this.attachers = attachers;
	}
}
