package picounit.util;

abstract public class Equals {
	public final boolean equals(Object lhs, Object rhs) {
		if (lhs == null || rhs == null || !lhs.getClass().equals(rhs.getClass())) {
			return false;
		}

		if (lhs == rhs) {
			return true;
		}

		return equalsImpl(lhs, rhs);
	}

	abstract protected boolean equalsImpl(Object lhs, Object rhs);
}