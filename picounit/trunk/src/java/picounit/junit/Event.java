package picounit.junit;

import picounit.util.Equals;

import java.lang.reflect.Method;

public class Event {
	private final Class type;
	private final Method method;

	public Event(Class type, Method method) {
		this.type = type;
		this.method = method;
	}

	public Class getType() {
		return type;
	}
	
	public Method getMethod() {
		return method;
	}

	public boolean equals(Object object) {
		return equals.equals(this, object);
	}
	
	public String toString() {
		return method.getName();
	}

	private Equals equals = new Equals() {
		protected boolean equalsImpl(Object lhs, Object rhs) {
			Event left = (Event) lhs;
			Event right = (Event) rhs;

			return left.type.equals(right.type) &&
				left.method.equals(right.method);
		}
	};
}
