package henix.jillus;

/**
 * Create a new value of record type T
 *
 * @author henix
 */
public interface RecordCreator<T> {
	public T create();
}
