package picounit;

public interface Verify {
	void fail();
	void fail(String message);

	void that(boolean expression);
	void that(String message, boolean expression);

	void not(boolean expression);
	void not(String message, boolean expression);

	void equal(boolean expected, boolean actual);
	void equal(String message, boolean expected, boolean actual);

	void equal(long expected, long actual);
	void equal(String message, long expected, long actual);

	void equal(Object expected, Object actual);
	void equal(String message, Object expected, Object actual);

	void notEqual(Object expected, Object actual);
	void notEqual(String message, Object expected, Object actual);

	void notEqual(boolean expected, boolean actual);
	void notEqual(String message, boolean expected, boolean actual);

	void notEqual(long expected, long actual);
	void notEqual(String message, long expected, long actual);
}
