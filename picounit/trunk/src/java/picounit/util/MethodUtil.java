package picounit.util;

import java.lang.reflect.Method;

public class MethodUtil {
	public static final Method hashCode = new MethodUtil().getMethod("hashCode");
	public static final Method equals = new MethodUtil().getMethod("equals", Object.class);
	public static final Method toString = new MethodUtil().getMethod("toString");

	public Method getMethod(String name) {
		return getMethod(Object.class, name);
	}

	public Method getMethod(String name, Class parameterType) {
		return getMethod(Object.class, name, parameterType);
	}

	public Method getMethod(Class clazz, String name) {
		return getMethod(clazz, name, new Class[0]);
	}

	public Method getMethod(Class clazz, String name, Class parameterType) {
		return getMethod(clazz, name, new Class[] {parameterType});
	}

	public Method getMethod(Class clazz, String name, Class firstParameterType,
		Class secondParameterType) {

		return getMethod(clazz, name, new Class[] {firstParameterType, secondParameterType});
	}

	private Method getMethod(Class clazz, String name, Class[] parameterTypes) {
		try {
			return clazz.getMethod(name, parameterTypes);
		}
		catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new RuntimeException(noSuchMethodException);
		}
	}
}
