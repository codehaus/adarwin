package picounit.around.mock;

import picounit.Mocker;
import picounit.Test;
import picounit.Verify;

import java.util.Arrays;

public class MockResolverTest implements Test {
	// Mocks
	private Mocker mocker;
	private Test test;

	// Unit
	private MockResolver mockResolver;

	public void mock(Mocker mocker, Test test) {
		this.mocker = mocker;
		this.test = test;

		this.mockResolver = new MockResolverImpl(mocker);
	}

	public void testGetComponent(Mocker mocker, Verify verify) {
		mocker.expectAndReturn(this.mocker.mock(Test.class), test);

		mocker.replay();

		verify.equal(test, mockResolver.getComponent(Test.class));
	}

	public void testGetComponents(Mocker mocker, Verify verify) {
		mocker.expectAndReturn(this.mocker.mock(Test.class), test);

		mocker.replay();

		verify.that(Arrays.equals(new Object[] {test},
			mockResolver.getComponents(new Class[] {Test.class})));
	}
}
