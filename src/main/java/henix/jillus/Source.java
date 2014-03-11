package henix.jillus;

public interface Source<M> {
	// read
	public boolean canGet();
	public boolean canGet(int len);
	public int available();
	public char getchar();
	public String gets(int len);
	/**
	 * The same as gets(prefix.length()).equals(prefix)
	 * but with better performance
	 * #6
	 */
	boolean startsWith(String prefix);

	// move pointer
	public void consume();
	public void consume(int n);

	/**
	 * A mark in source, you can either:
	 *
	 * 1. goback() to it only ONCE
	 * 2. tillNow() get the string from mark to current position, only ONCE
	 * 3. cancel it, then it become invalid, you can not use it to do 1. or 2.
	 *
	 * If you created a mark, one and only one of above 3 actions MUST be done on it.
	 */
	public M mark();
	public void goback(M mark);
	public void cancel(M mark);
	public String tillNow(M mark);
}
