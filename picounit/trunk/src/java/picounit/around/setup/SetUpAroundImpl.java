package picounit.around.setup;

import picounit.Suite;
import picounit.Test;
import picounit.impl.MethodInvoker;
import picounit.impl.PicoResolver;

import java.lang.reflect.Method;


public class SetUpAroundImpl implements SetUpAround {
	private final MethodInvoker methodInvoker;
	private final PicoResolver picoResolver;

	public SetUpAroundImpl(MethodInvoker methodInvoker, PicoResolver picoResolver) {
		this.methodInvoker = methodInvoker;
		this.picoResolver = picoResolver;
	}

	public void before(Object object, Method method) {
		if (matches(object)) {
			methodInvoker.invokeMatchingMethods(object, "setUp", picoResolver);
		}
	}

	public void after(Object object, java.lang.reflect.Method method) {
		if (matches(object)) {
			methodInvoker.invokeMatchingMethods(object, "tearDown", picoResolver);
		}
	}
	
	private boolean matches(Object object) {
		return object instanceof Test ||
			object instanceof Suite;
	}
}
