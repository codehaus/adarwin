package picounit.suite;

import picounit.Dependancy;
import picounit.Suite;
import picounit.util.MethodUtil;

import java.lang.reflect.Method;

public interface SuiteInstance extends Suite {
	void suiteOne();

	void suiteTwo(Dependancy dependancy);
	
	Method suiteOne = new MethodUtil().getMethod(SuiteInstance.class, "suiteOne");

	Method suiteTwo = new MethodUtil().getMethod(SuiteInstance.class, "suiteTwo", Dependancy.class);
}