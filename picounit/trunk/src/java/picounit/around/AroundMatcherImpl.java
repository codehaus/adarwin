package picounit.around;


public class AroundMatcherImpl implements AroundMatcher {
	public boolean matches(Class testClass) {
		return Around.class.isAssignableFrom(testClass);
	}
}
