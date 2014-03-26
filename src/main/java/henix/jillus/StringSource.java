package henix.jillus;

public class StringSource implements Source {

	private final String input;

	private int pos;
	private int endpos;

	public StringSource(String input) {
		this.input = input;

		this.pos = 0;
		this.endpos = input.length();
	}

	public boolean canGet() {
		return pos < endpos;
	}

	public boolean canGet(int len) {
		return pos + len <= endpos;
	}

	public int available() {
		return endpos - pos;
	}

	public char getchar() {
		return input.charAt(pos);
	}

	public String gets(int len) {
		return input.substring(pos, pos + len);
	}

	public boolean startsWith(String prefix) {
		return input.regionMatches(pos, prefix, 0, prefix.length());
	}

	public void consume() {
		pos++;
	}

	public void consume(int n) {
		pos += n;
	}

	public int mark() {
		return pos;
	}

	public void goback(int mark) {
		if (mark > pos) {
			throw new IllegalArgumentException("goback can only goto previous pos: from " + pos + " can't goto " + mark);
		}
		pos = mark;
	}

	public void cancel(int mark) {
	}

	public String tillNow(int mark) {
		return input.substring(mark, pos);
	}
}
