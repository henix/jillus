package henix.jillus.pegs.attacher;

public class OrderChoiceAttacher<T> extends Attacher<T> {

	public final Attacher<? super T>[] attachers;

	public OrderChoiceAttacher(Attacher<? super T>... attachers) {
		this.attachers = attachers;
	}
}
