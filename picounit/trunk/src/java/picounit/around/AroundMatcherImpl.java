package picounit.around;

import picounit.impl.Empty;

public class AroundMatcherImpl extends Empty implements AroundMatcher {
	public boolean matches(Class testClass) {
		return Around.class.isAssignableFrom(testClass);
	}
}
