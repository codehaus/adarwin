package picounit.impl;

import picounit.around.AroundRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodRunnerImpl implements MethodRunner {
	private final UserPicoResolver picoResolver;
	private final AroundRunner aroundRunner;
	private final ResultListener resultListener;

	public MethodRunnerImpl(UserPicoResolver picoResolver, AroundRunner aroundRunner,
		ResultListener resultListener) {

		this.picoResolver = picoResolver;
		this.aroundRunner = aroundRunner;
		this.resultListener = resultListener;
	}

	public void runWrapped(Class someClass, Method method, Scope scope) {
		try {
			Object object = picoResolver.getComponent(someClass);

			aroundRunner.before(object, method);

			runMethod(method, scope, object);

			aroundRunner.after(object, method);
		}
		catch (PicoUnitException picoUnitException) {
			resultListener.error(scope, picoUnitException);
		}
	}

	private void runMethod(Method method, Scope scope, Object object) {
		try {
			resultListener.enter(scope);

			method.invoke(object, picoResolver.getComponents(method.getParameterTypes()));

			resultListener.exit();
		}
		catch (InvocationTargetException invocationTargetException) {
			resultListener.exit(invocationTargetException.getTargetException());
		}
		catch (PicoUnitException picoUnitException) {
			resultListener.error(scope, picoUnitException);
		}
		catch (Throwable throwable) {
			resultListener.exit(throwable);
		}
	}
}