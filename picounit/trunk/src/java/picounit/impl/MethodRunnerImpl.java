package picounit.impl;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodRunnerImpl implements MethodRunner {
	public void invokeMatchingMethods(Class someClass, String prefix, ScopeFactory scopeFactory) {
		scopeFactory.enterClass(someClass);
		
		Method[] methods = someClass.getMethods();

		for (int index = 0; index < methods.length; index++) {
			Method method = methods[index];

			if (method.getName().startsWith(prefix)) {
				scopeFactory.runMethod(method);
			}
		}
		
		scopeFactory.exit();
	}

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
}