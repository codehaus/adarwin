package picounit.around.mock;

import picounit.Mocker;

public class MockResolverImpl implements MockResolver {
	private final Mocker mocker;

	public MockResolverImpl(Mocker mocker) {
		this.mocker = mocker;
	}

	public Object getComponent(Class componentClass) {
		return mocker.mock(componentClass);
	}

	public Object[] getComponents(Class[] componentClasses) {
		Object[] componenets = new Object[componentClasses.length];

		for (int index = 0; index < componenets.length; index++) {
			componenets[index] = getComponent(componentClasses[index]);
		}

		return componenets;
	}
}
