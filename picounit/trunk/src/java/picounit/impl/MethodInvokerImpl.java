package picounit.impl;

import picounit.around.AroundRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodInvokerImpl implements MethodInvoker {
	private final UserPicoResolver picoResolver;
	private final AroundRunner aroundRunner;

	public MethodInvokerImpl(UserPicoResolver picoResolver, ResultListener resultListener,
		AroundRunner aroundRunner) {

		this.picoResolver = picoResolver;
		this.aroundRunner = aroundRunner;
	}

	public void invokeMethod(Method method, ResultListener resultListener) {
		try {
			Object object = picoResolver.getComponent(method.getDeclaringClass());

			aroundRunner.before(object, method);

			method.invoke(object, picoResolver.getComponents(method.getParameterTypes()));
		
			aroundRunner.after(object, method);
		}
		catch (InvocationTargetException invocationTargetException) {
			resultListener.exit(invocationTargetException.getTargetException());
		}
		catch (Throwable throwable) {
			resultListener.exit(throwable);
		}
	}
}