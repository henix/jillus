package henix.jillus;

/**
 * Create a value of type T from the captured text
 *
 * @author henix
 */
public interface ValueCreator<T> {
	public T create(String capture);
}
