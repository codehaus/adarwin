package picounit.around.mock;

import picounit.Mocker;
import picounit.impl.MethodInvoker;

import java.lang.reflect.Method;


public class MockAroundImpl implements MockAround {
	private final Mocker mocker;
	private final MockResolver mockResolver;
	private final MethodInvoker methodInvoker;

	public MockAroundImpl(Mocker mocker, MockResolver mockResolver,
		MethodInvoker methodInvoker) {

		this.mocker = mocker;
		this.mockResolver = mockResolver;
		this.methodInvoker = methodInvoker;
	}

	public void before(Object object, Method method) {
		mocker.reset();

		methodInvoker.invokeMatchingMethods(object, "mock", mockResolver);
	}

	public void after(Object object, java.lang.reflect.Method method) {
		mocker.verify();
	}
}
