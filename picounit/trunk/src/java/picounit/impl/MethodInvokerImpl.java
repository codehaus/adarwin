package picounit.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvokerImpl implements MethodInvoker {
	public void invokeMatchingMethods(Object object, String pattern, Resolver resolver) {
		Method[] methods = object.getClass().getMethods();
		for (int index = 0; index < methods.length; index++) {
			Method method = methods[index];

			if (method.getName().startsWith(pattern)) {
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
			}
		}
	}
}