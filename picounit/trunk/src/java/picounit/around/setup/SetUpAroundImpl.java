package picounit.around.setup;

import picounit.impl.MethodInvoker;
import picounit.test.PicoResolver;

import java.lang.reflect.Method;


public class SetUpAroundImpl implements SetUpAround {
	private final MethodInvoker methodInvoker;
	private final PicoResolver picoResolver;

	public SetUpAroundImpl(MethodInvoker methodInvoker, PicoResolver picoResolver) {
		this.methodInvoker = methodInvoker;
		this.picoResolver = picoResolver;
	}

	public void before(Object object, Method method) {
		methodInvoker.invokeMatchingMethods(object, "setUp", picoResolver);
	}

	public void after(Object object, java.lang.reflect.Method method) {
		methodInvoker.invokeMatchingMethods(object, "tearDown", picoResolver);
	}
}
