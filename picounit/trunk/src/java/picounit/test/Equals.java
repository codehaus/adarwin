package picounit.test;

abstract public class Equals {
	private final Object thisObject;

	public Equals(Object thisObject) {
		this.thisObject = thisObject;
	}

	public final boolean equals(Object object) {
		if (object == null || !object.getClass().equals(thisObject.getClass())) {
			return false;
		}
		
		if (thisObject == object) {
			return true;
		}
		
		return equalsImpl(object);
	}
	
	abstract protected boolean equalsImpl(Object object);
}