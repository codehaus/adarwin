package picounit;

import picounit.util.MethodUtil;

import java.lang.reflect.Method;


public interface TestInstance extends Test {
	void testOne();

	void testTwo(Dependancy dependancy);

	void nonTestMethod();

	Method testOne = new MethodUtil().getMethod(TestInstance.class, "testOne");
	Method testTwo = new MethodUtil().getMethod(TestInstance.class, "testTwo", Dependancy.class);
}