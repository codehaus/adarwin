package picounit.impl;

import picounit.Verify;

import java.util.Arrays;

import junit.framework.AssertionFailedError;

public class VerifyImpl implements Verify {
	public void fail() {
		throw new AssertionFailedError();
	}
	
	public void fail(String message) {
		throw new AssertionFailedError(message);
	}

	public void that(boolean expression) {
		if (!expression) {
			fail(message(expression, !expression));
		}
	}
	
	public void that(String message, boolean expression) {
		if (!expression) {
			fail(message(message, expression, !expression));
		}
	}
	
	public void not(boolean expression) {
		if (expression) {
			fail(message(!expression, expression));
		}
	}

	public void not(String message, boolean expression) {
		if (expression) {
			fail(message);
		}
	}

	public void equal(boolean expected, boolean actual) {
		if (expected != actual) {
			fail(message(expected, actual));
		}
	}

	public void equal(String message, boolean expected, boolean actual) {
		if (expected != actual) {
			fail(message(message, expected, actual));
		}
	}

	public void equal(long expected, long actual) {
		if (expected != actual) {
			fail(message(expected, actual));
		}
	}
	
	public void equal(String message, long expected, long actual) {
		if (expected != actual) {
			fail(message(message, expected, actual));
		}
	}

	public void equal(Object expected, Object actual) {
		equal(null, expected, actual);
	}

	public void equal(String message, Object expected, Object actual) {
		boolean fail = false;

		if (expected == actual) {
			return;
		}
		if (oneNullOtherNotNull(expected, actual)) {
			fail = true;
		}
		else if (expected.getClass().isArray() && actual.getClass().isArray()) {
			if (!Arrays.equals((Object[]) expected, (Object[]) actual)) {
				fail = true;
			}
		}
		else if (!expected.equals(actual)) {
			fail = true;
		}

		if (fail) {
			fail(message(message, expected, actual));
		}
	}

	public void notEqual(boolean expected, boolean actual) {
		if (expected == actual) {
			fail(notEqualMessage(expected));
		}
	}

	public void notEqual(String message, boolean expected, boolean actual) {
		if (expected == actual) {
			fail(notEqualMessage(message, expected));
		}
	}

	public void notEqual(long expected, long actual) {
		if (expected == actual) {
			fail(notEqualMessage(expected));
		}
	}

	public void notEqual(String message, long expected, long actual) {
		if (expected == actual) {
			fail(notEqualMessage(message, expected));
		}
	}

	public void notEqual(Object expected, Object actual) {
		if (expected.equals(actual)) {
			fail(notEqualMessage(expected));
		}
	}

	public void notEqual(String message, Object expected, Object actual) {
		if(expected.equals(actual)) {
			fail(notEqualMessage(message, expected));
		}
	}
	
	private String message(boolean expected, boolean actual) {
		return message(new Boolean(expected), new Boolean(actual));
	}

	private String message(String message, boolean expected, boolean actual) {
		return message(message, new Boolean(expected), new Boolean(actual));
	}

	private String message(long expected, long actual) {
		return message(null, new Long(expected), new Long(actual));
	}

	private String message(String message, long expected, long actual) {
		return message(message, new Long(expected), new Long(actual));
	}
	
	private String message(Object expected, Object actual) {
		return "expected: <" + toString(expected) + ">, but was: <" + toString(actual) + ">";
	}

	private String message(String message, Object expected, Object actual) {
		String mainString = "expected: <" + toString(expected) + ">, but was: <" + toString(actual) + ">";

		if (message != null && message.length() != 0) {
			return message + ", " + mainString;
		}
		else {
			return mainString;
		}
	}
	
	private String notEqualMessage(boolean expected) {
		return notEqualMessage(new Boolean(expected));
	}
	
	private String notEqualMessage(long expected) {
		return notEqualMessage(new Long(expected));
	}

	private String notEqualMessage(Object expected) {
		return "expected not equal: <" + expected + ">";
	}
	
	private String notEqualMessage(String message, boolean expected) {
		return notEqualMessage(message, new Boolean(expected));
	}

	private String notEqualMessage(String message, long expected) {
		return notEqualMessage(message, new Long(expected));
	}
	
	private String notEqualMessage(String message, Object expected) {
		return message + ", " + "expected not equal: <" + expected + ">";
	}

	private String toString(Object object) {
		if (object == null) {
			return "null";
		}
		else if (object.getClass().isArray()) {
			Object[] array = (Object[]) object;
			
			return Arrays.asList(array).toString();
		}
		else {
			return object.toString();
		}
	}

	private boolean oneNullOtherNotNull(Object expected, Object actual) {
		return (expected == null && actual != null) ||
			(expected != null && actual == null);
	}
}
