package picounit.around.setup;

import picounit.MainRunner;
import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.impl.MethodRunner;
import picounit.impl.PicoResolver;

public class SetUpAroundTest implements Test {
	// Mocks
	private TestInstance testInstance;
	private PicoResolver picoResolver;
	private MethodRunner methodRunner;

	// Unit
	private SetUpAround setUpAround;

	public void mock(TestInstance testInstance, PicoResolver picoResolver,
		MethodRunner methodRunner) {

		this.testInstance = testInstance;
		this.picoResolver = picoResolver;
		this.methodRunner = methodRunner;
		
		this.setUpAround = new SetUpAroundImpl(methodRunner, picoResolver);
	}
	
	public void testBeforeInvokesSetUpMethods(Mocker mocker) {
		methodRunner.invokeMethod(testInstance, "setUp", picoResolver);
		
		mocker.replay();
		
	 	setUpAround.before(testInstance, TestInstance.testOne);
	}

	public void testAfterInvokesTearDownMethods(Mocker mocker) {
		methodRunner.invokeMethod(testInstance, "tearDown", picoResolver);

		mocker.replay();

		setUpAround.after(testInstance, TestInstance.testOne);
	}

	public void testCallsSetupBeforeAndTearDownAfterEachTestMethod(Verify verify) {
		StringBuffer invocations = new StringBuffer();

		MainRunner.create()
			.registerFixture(invocations)
			.run(ExampleTest.class);

		verify.equal("setUp testOne tearDown setUp testTwo tearDown ", invocations.toString());
	}
	
	public static class ExampleTest implements Test {
		private final StringBuffer invocations;

		public ExampleTest(StringBuffer invocations) {
			this.invocations = invocations;
		}

		public void setUp() {
			invocations.append("setUp ");
		}

		public void tearDown() {
			invocations.append("tearDown ");
		}

		public void testOne() {
			invocations.append("testOne ");
		}

		public void testTwo() {
			invocations.append("testTwo ");
		}
	}
}
