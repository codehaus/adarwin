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

	/*
	 * This method invokes all matching methods, but invokes the arounds first.
	 */
	public void invokeMatchingMethods(Class someClass, String prefix, ScopeFactory scopeFactory) {
		resultListener.enter(scopeFactory.createClassScope(someClass));

		Method[] methods = someClass.getMethods();

		for (int index = 0; index < methods.length; index++) {
			Method method = methods[index];

			if (method.getName().startsWith(prefix)) {
				runWrapped(someClass, method, scopeFactory.createMethodScope(method));
			}
		}

		resultListener.exit();
	}

	/*
	 * This method invokes one matching method
	 */
	public void invokeMethod(Object object, String methodName, Resolver resolver) {
		Method[] methods = object.getClass().getMethods();

		for (int index = 0; index < methods.length; index++) {
			Method method = methods[index];

			if (method.getName().equals(methodName)) {
				try {
					method.invoke(object, resolver.getComponents(method.getParameterTypes()));
				}
				catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				break;
			}
		}
	}

	private void runWrapped(Class someClass, Method method, Scope scope) {
		try {
			Object object = picoResolver.getComponent(someClass);

			aroundRunner.before(object, method);

			runMethod(method, object, scope);

			aroundRunner.after(object, method);
		}
		catch (PicoUnitException picoUnitException) {
			resultListener.error(scope, picoUnitException);
		}
	}

	private void runMethod(Method method, Object object, Scope scope) {
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