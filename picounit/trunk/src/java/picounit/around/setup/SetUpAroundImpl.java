package picounit.around.setup;

import picounit.Suite;
import picounit.Test;
import picounit.impl.MethodRunner;
import picounit.impl.PicoResolver;

import java.lang.reflect.Method;


public class SetUpAroundImpl implements SetUpAround {
	private final PicoResolver picoResolver;
	private final MethodRunner methodRunner;

	public SetUpAroundImpl(MethodRunner methodRunner, PicoResolver picoResolver) {
		this.picoResolver = picoResolver;
		this.methodRunner = methodRunner;
	}

	public void before(Object object, Method method) {
		if (matches(object)) {
			methodRunner.invokeMethod(object, "setUp", picoResolver);
		}
	}

	public void after(Object object, java.lang.reflect.Method method) {
		if (matches(object)) {
			methodRunner.invokeMethod(object, "tearDown", picoResolver);
		}
	}

	private boolean matches(Object object) {
		return object instanceof Test ||
			object instanceof Suite;
	}
}
