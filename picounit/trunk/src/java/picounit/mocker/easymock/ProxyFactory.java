package picounit.mocker.easymock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory {
	public Object create(Class clazz, InvocationHandler invocationHandler) {
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, invocationHandler);
	}
}
