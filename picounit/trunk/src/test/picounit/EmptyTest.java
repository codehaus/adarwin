package picounit;

import picounit.impl.Empty;

public class EmptyTest implements Test {
	// Unit
	private final Empty empty = new Empty();

	public void testHashCode(Verify verify) {
		verify.equal(empty.getClass().hashCode(), empty.hashCode());
	}

	public void testEquals(Verify verify) {
		verify.equal("Should equal self", empty, empty);

		verify.notEqual("Should not equal null", empty, null);

		verify.notEqual("Should not equal objects of other classes", empty, new Object());

		verify.equal("Should equal object of the same class", empty, new Empty());
	}
}
