package picounit.around.mock;

import picounit.Mocker;
import picounit.Test;
import picounit.impl.MethodRunner;

import java.lang.reflect.Method;


public class MockAroundImpl implements MockAround {
	private final Mocker mocker;
	private final MockResolver mockResolver;
	private final MethodRunner methodRunner;

	public MockAroundImpl(Mocker mocker, MockResolver mockResolver,
		MethodRunner methodRunner) {

		this.mocker = mocker;
		this.mockResolver = mockResolver;
		this.methodRunner = methodRunner;
	}

	public void before(Object object, Method method) {
		if (matches(object)) {	
			mocker.reset();
	
			methodRunner.invokeMethod(object, "mock", mockResolver);
		}
	}

	public void after(Object object, java.lang.reflect.Method method) {
		if (matches(object)) {
			mocker.verify();
		}
	}

	private boolean matches(Object object) {
		return object instanceof Test;
	}
}
