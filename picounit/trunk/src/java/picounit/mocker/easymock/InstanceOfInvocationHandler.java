package picounit.mocker.easymock;

import picounit.util.MethodUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InstanceOfInvocationHandler implements InvocationHandler {
	private final Class interfaceClass;

	public InstanceOfInvocationHandler(Class interfaceClass) {
		assert interfaceClass != null;

		this.interfaceClass = interfaceClass;
	}

	public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
		if (MethodUtil.hashCode.equals(method)) {
			return new Integer(interfaceClass.hashCode());
		}

		if (MethodUtil.equals.equals(method)) {
			return new Boolean(interfaceClass.isAssignableFrom(arguments[0].getClass()));
		}

		if (MethodUtil.toString.equals(method)) {
			return "instance of " + interfaceClass.getName();
		}

		throw new UnsupportedOperationException(method.toString() + Arrays.asList(arguments));
	}

	public Class getInterfaceClass() {
		return interfaceClass;
	}
}
