package picounit.around;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.around.mock.MockAround;
import picounit.around.setup.SetUpAround;
import picounit.impl.PicoResolver;
import picounit.impl.ResultListener;

import java.lang.reflect.Method;


public class AroundRunnerTest implements Test {
	// Mocks
	private PicoResolver picoResolver;
	private TestInstance testInstance;
	private MockAround mockAround;
	private SetUpAround setUpAround;

	// Unit
	private AroundRunnerImpl aroundRunner;

	public void mock(PicoResolver picoResolver, TestInstance testInstance, MockAround mockAround,
		SetUpAround setUpAround, ResultListener resultListener) {

		this.picoResolver = picoResolver;
		this.testInstance = testInstance;
		this.mockAround = mockAround;
		this.setUpAround = setUpAround;

		this.aroundRunner = new AroundRunnerImpl(picoResolver, resultListener);
	}

	public void testBefore(Mocker mocker) {
		mocker.expectAndReturn(picoResolver.getComponent(SetUpAround.class), setUpAround);
		mocker.expectAndReturn(picoResolver.getComponent(MockAround.class), mockAround);

		setUpAround.before(testInstance, TestInstance.testOne);

		mockAround.before(testInstance, TestInstance.testOne);

	 	mocker.replay();

		aroundRunner.registryEvent(SetUpAround.class);
		aroundRunner.registryEvent(MockAround.class);
	 	aroundRunner.before(testInstance, TestInstance.testOne);
	}
	
	public void testAfterRunsAroundsInInverseOrder(Mocker mocker, Verify verify) {
		final StringBuffer results = new StringBuffer();

		Around setUpAround = new Around() {
			public void before(Object object, Method method) {
			}

			public void after(Object object, java.lang.reflect.Method method) {
				results.append("setUp ");
			}
		};

		Around mockAround = new Around() {
			public void before(Object object, Method method) {
			}

			public void after(Object object, java.lang.reflect.Method method) {
				results.append("mock ");
			}
		};
		
		mocker.expectAndReturn(picoResolver.getComponent(mockAround.getClass()), mockAround);
		mocker.expectAndReturn(picoResolver.getComponent(setUpAround.getClass()), setUpAround);

		mocker.replay();

		aroundRunner.registryEvent(setUpAround.getClass());
		aroundRunner.registryEvent(mockAround.getClass());
		aroundRunner.after(testInstance, TestInstance.testOne);
		
		verify.equal("Arounds not run in inverse order", "mock setUp ", results.toString());
	}
}
