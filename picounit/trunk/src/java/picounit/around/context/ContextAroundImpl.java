package picounit.around.context;

import picounit.Suite;
import picounit.impl.Registry;

import java.lang.reflect.Method;


public class ContextAroundImpl implements ContextAround {
	private final Registry registry;

	public ContextAroundImpl(Registry registry) {
		this.registry = registry;
	}

	public void before(Object object, Method method) {
		if (matches(object)) {
			registry.push();
		}
	}

	public void after(Object object, java.lang.reflect.Method method) {
		if (matches(object)) {
			registry.pop();
		}
	}

	private boolean matches(Object object) {
		return object instanceof Suite;
	}
}
