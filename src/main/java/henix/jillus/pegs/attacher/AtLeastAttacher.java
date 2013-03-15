package henix.jillus.pegs.attacher;

public class AtLeastAttacher<T> extends Attacher<T> {

	public final int n;
	public final Attacher<T> e;

	public AtLeastAttacher(int n, Attacher<T> e) {
		this.n = n;
		this.e = e;
	}
}
