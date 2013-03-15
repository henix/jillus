package henix.jillus;

public interface Source {
	// read
	public boolean canGet();
	public boolean canGet(int len);
	public char getchar();
	public String gets(int len);

	// move pointer
	public void consume();
	public void consume(int n);

	// mark
	public Mark mark();
	public void goback(Mark mark);
	public void cancel(Mark mark);
	public String tillNow(Mark mark);
}
