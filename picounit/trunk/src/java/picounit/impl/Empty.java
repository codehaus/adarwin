package picounit.impl;

public class Empty {
	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null && (object == this || object.getClass().equals(getClass()));
	}
}
