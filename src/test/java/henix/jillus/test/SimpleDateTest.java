package henix.jillus.test;

import static henix.jillus.Pegs.*;

import henix.jillus.Capturer;
import henix.jillus.JillusSyntaxException;
import henix.jillus.PatternExecutor;
import henix.jillus.PegPattern;
import henix.jillus.StringSource;
import henix.jillus.ValueCreator;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class SimpleDateTest {

	public static class MyDate {
		public int year;
		public int month;
		public int day;
	}

	static final ValueCreator<Integer> intParser = new ValueCreator<Integer>() {
		public Integer create(String capture) {
			return Integer.valueOf(capture);
		}
	};

	static final PegPattern digit = charInRange('0', '9');

	static final Capturer<Integer> aint = capture(intParser, atLeast(1, digit)); // [0-9]+

	static final Capturer<MyDate> mydate = asStruct(MyDate.class, sequence(
		bindField(MyDate.class, Integer.class, "year", aint),
		bindNothing("-"),
		bindField(MyDate.class, Integer.class, "month", aint),
		bindNothing("-"),
		bindField(MyDate.class, Integer.class, "day", aint)
	));

	@Test
	public void main() {
		final PatternExecutor executor = new PatternExecutor(new StringSource("3001-4-1"));
		final MyDate result = executor.execute(mydate);

		Assert.assertEquals(3001, result.year);
		Assert.assertEquals(4, result.month);
		Assert.assertEquals(1, result.day);
	}

	@Test
	public void error() {
		final PatternExecutor executor = new PatternExecutor(new StringSource("3001+4-1"));
		try {
			final MyDate result = executor.execute(mydate);
			Assert.fail("Didn't throw exception");
		} catch (JillusSyntaxException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.containsString("-"));
		}
	}
}
