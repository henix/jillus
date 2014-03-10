package henix.jillus;

import henix.jillus.pegs.attacher.AtLeastAttacher;
import henix.jillus.pegs.attacher.Attacher;
import henix.jillus.pegs.attacher.CaptureAttacher;
import henix.jillus.pegs.attacher.NothingAttacher;
import henix.jillus.pegs.attacher.OrderChoiceAttacher;
import henix.jillus.pegs.attacher.SequenceAttacher;
import henix.jillus.pegs.capture.AtomicCapturer;
import henix.jillus.pegs.capture.CompoundCapturer;
import henix.jillus.pegs.capture.NonTerminalCapturer;
import henix.jillus.pegs.capture.OrderChoiceCapturer;
import henix.jillus.pegs.capture.PassingCapturer;
import henix.jillus.pegs.pattern.AnyChar;
import henix.jillus.pegs.pattern.AtLeast;
import henix.jillus.pegs.pattern.CharInRange;
import henix.jillus.pegs.pattern.CharInSet;
import henix.jillus.pegs.pattern.NotPredict;
import henix.jillus.pegs.pattern.Literal;
import henix.jillus.pegs.pattern.NonTerminal;
import henix.jillus.pegs.pattern.Optional;
import henix.jillus.pegs.pattern.OrderChoice;
import henix.jillus.pegs.pattern.Sequence;

/**
 * The interpreter
 */
public class PatternExecutor {

	private Source src;

	public PatternExecutor(Source src) {
		this.src = src;
	}

	private void error(String desc) {
		final String current = src.canGet() ? src.gets(Math.min(10, src.available())) : "<eof>";
		throw new JillusSyntaxException("Expect " + desc + ", but saw: " + current);
	}

	private boolean match(PegPattern e, boolean mustSuccess) {
		if (e instanceof AnyChar) {
			if (src.canGet()) {
				src.consume();
				return true;
			}
			if (mustSuccess) {
				error("any char");
			}
			return false;
		} else if (e instanceof CharInRange) {
			final CharInRange patt = (CharInRange)e;
			if (src.canGet()) {
				final char c = src.getchar();
				if (c >= patt.from && c <= patt.to) {
					src.consume();
					return true;
				}
			}
			if (mustSuccess) {
				error("a char in [" + patt.from + "-" + patt.to + "]");
			}
			return false;
		} else if (e instanceof CharInSet) {
			final CharInSet patt = (CharInSet)e;
			if (src.canGet()) {
				final char c = src.getchar();
				if (patt.str.indexOf(c) != -1) {
					src.consume();
					return true;
				}
			}
			if (mustSuccess) {
				error("a char in [" + patt.str + "]");
			}
			return false;
		} else if (e instanceof Literal) {
			final Literal patt = (Literal)e;
			final int len = patt.str.length();
			if (src.canGet(len)) {
				if (src.startsWith(patt.str)) {
					src.consume(len);
					return true;
				}
			}
			if (mustSuccess) {
				error("literal " + patt.str);
			}
			return false;
		} else if (e instanceof Optional) {
			final Optional patt = (Optional)e;
			match(patt.e, false);
			return true;
		} else if (e instanceof NotPredict) {
			final NotPredict patt = (NotPredict)e;
			final Mark mark = src.mark();
			if (!match(patt.e, false)) {
				src.cancel(mark);
				return true;
			}
			src.goback(mark);
			return false;
		} else if (e instanceof Sequence) {
			final Sequence patt = (Sequence)e;
			final Mark mark = src.mark();
			for (PegPattern subpatt : patt.patts) {
				if (!match(subpatt, mustSuccess)) {
					src.goback(mark);
					return false;
				}
			}
			src.cancel(mark);
			return true;
		} else if (e instanceof OrderChoice) {
			final OrderChoice patt = (OrderChoice)e;
			int i = 0;
			for (; i < patt.patts.length - 1; i++) {
				final PegPattern subpatt = patt.patts[i];
				if (match(subpatt, false)) {
					return true;
				}
			}
			for (; i < patt.patts.length; i++) {
				final PegPattern subpatt = patt.patts[i];
				if (match(subpatt, mustSuccess)) {
					return true;
				}
			}
			return false;
		} else if (e instanceof AtLeast) {
			final AtLeast patt = (AtLeast)e;
			final Mark mark = src.mark();
			for (int i = 0; i < patt.n; i++) {
				if (!match(patt.e, mustSuccess)) {
					src.goback(mark);
					return false;
				}
			}
			while (match(patt.e, false)) {
				;
			}
			src.cancel(mark);
			return true;
		} else if (e instanceof NonTerminal) {
			final NonTerminal patt = (NonTerminal)e;
			if (patt.actual == null) {
				throw new IllegalArgumentException("NonTerminal uninitialized, call set() before use it");
			}
			return match(patt.actual, mustSuccess);
		} else {
			throw new IllegalArgumentException("Unknown PegPattern type: " + e.getClass().getName());
		}
	}

	public boolean match(PegPattern e) {
		return match(e, true);
	}

	private <E> E execute(Capturer<E> e, boolean mustSuccess) {
		if (e instanceof AtomicCapturer<?>) {
			final AtomicCapturer<E> c = (AtomicCapturer<E>)e;
			final Mark mark = src.mark();
			if (match(c.e, mustSuccess)) {
				return c.valueCreator.create(src.tillNow(mark));
			}
			src.cancel(mark);
			return null;
		} else if (e instanceof PassingCapturer<?>) {
			final PassingCapturer<E> c = (PassingCapturer<E>)e;
			final Mark mark = src.mark();
			E ret = null;
			if (c.before != null && !match(c.before, mustSuccess)) {
				src.goback(mark);
				return null;
			}
			ret = execute(c.e, mustSuccess);
			if (ret == null) {
				src.goback(mark);
				return null;
			}
			if (c.after != null && !match(c.after, mustSuccess)) {
				src.goback(mark);
				return null;
			}
			src.cancel(mark);
			return ret;
		} else if (e instanceof CompoundCapturer<?>) {
			final CompoundCapturer<E> c = (CompoundCapturer<E>)e;
			final E newObj = c.recordCreator.create();
			if (execute(c.a, newObj, mustSuccess)) {
				return newObj;
			}
			return null;
		} else if (e instanceof OrderChoiceCapturer<?>) {
			final OrderChoiceCapturer<E> c = (OrderChoiceCapturer<E>)e;
			int i = 0;
			for (; i < c.alternatives.length - 1; i++) {
				final Capturer<? extends E> patt = c.alternatives[i];
				final E ret = execute(patt, false);
				if (ret != null) {
					return ret;
				}
			}
			for (; i < c.alternatives.length; i++) {
				final Capturer<? extends E> patt = c.alternatives[i];
				final E ret = execute(patt, mustSuccess);
				if (ret != null) {
					return ret;
				}
			}
			return null;
		} else if (e instanceof NonTerminalCapturer<?>) {
			final NonTerminalCapturer<E> c = (NonTerminalCapturer<E>)e;
			if (c.actual == null) {
				throw new IllegalArgumentException("NonTerminalCapturer uninitialized, call set() before use it");
			}
			return execute(c.actual, mustSuccess);
		} else {
			throw new IllegalArgumentException("Unknown Capturer type: " + e.getClass().getName());
		}
	}

	public <E> E execute(Capturer<E> e) {
		return execute(e, true);
	}

	private <E> boolean execute(Attacher<E> e, E parentObj, boolean mustSuccess) {
		if (e instanceof NothingAttacher) {
			final NothingAttacher a = (NothingAttacher)e;
			return match(a.e, mustSuccess);
		} else if (e instanceof CaptureAttacher<?, ?>) {
			final CaptureAttacher<E, Object> a = (CaptureAttacher<E, Object>)e;
			final Object inner = execute(a.c, mustSuccess);
			if (inner != null) {
				a.fieldSetter.setValue(parentObj, inner);
				return true;
			}
			return false;
		} else if (e instanceof SequenceAttacher<?>) {
			final SequenceAttacher<E> a = (SequenceAttacher<E>)e;
			final Mark mark = src.mark();
			for (Attacher<? super E> attacher : a.attachers) {
				if (!execute(attacher, parentObj, mustSuccess)) {
					src.goback(mark);
					return false;
				}
			}
			src.cancel(mark);
			return true;
		} else if (e instanceof OrderChoiceAttacher<?>) {
			final OrderChoiceAttacher<E> a = (OrderChoiceAttacher<E>)e;
			int i = 0;
			for (; i < a.attachers.length - 1; i++) {
				final Attacher<? super E> attacher = a.attachers[i];
				if (execute(attacher, parentObj, false)) {
					return true;
				}
			}
			for (; i < a.attachers.length; i++) {
				final Attacher<? super E> attacher = a.attachers[i];
				if (execute(attacher, parentObj, mustSuccess)) {
					return true;
				}
			}
			return false;
		} else if (e instanceof AtLeastAttacher<?>) {
			final AtLeastAttacher<E> a = (AtLeastAttacher<E>)e;
			final Mark mark = src.mark();
			for (int i = 0; i < a.n; i++) {
				if (!execute(a.e, parentObj, mustSuccess)) {
					src.goback(mark);
					return false;
				}
			}
			while (execute(a.e, parentObj, false)) {
				;
			}
			src.cancel(mark);
			return true;
		} else {
			throw new IllegalArgumentException("Unknown Attacher type: " + e.getClass().getName());
		}
	}
}
