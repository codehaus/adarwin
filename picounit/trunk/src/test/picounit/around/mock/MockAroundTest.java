package picounit.around.mock;

import picounit.MainRunner;
import picounit.Mocker;
import picounit.Runner;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.impl.MethodRunner;

public class MockAroundTest implements Test {
	// Mocks
	private Mocker mocker;
	private MockResolver mockResolver;
	private TestInstance testInstance;
	private MethodRunner methodRunner;

	// Unit
	private MockAround mockAround;

	public void mock(Mocker mocker, MockResolver mockResolver, TestInstance testInstance,
		MethodRunner methodRunner) {

		this.mocker = mocker;
		this.mockResolver = mockResolver;
		this.testInstance = testInstance;
		this.methodRunner = methodRunner;

		this.mockAround = new MockAroundImpl(mocker, mockResolver, methodRunner);
	}
	
	public void testBefore(Mocker mocker) {
		this.mocker.reset();
		methodRunner.invokeMethod(testInstance, "mock", mockResolver); 

	 	mocker.replay();

	 	mockAround.before(testInstance, TestInstance.testOne);
	}

	public void testAfter(Mocker mocker) {
		this.mocker.verify();

		mocker.replay();

		mockAround.after(testInstance, TestInstance.testOne);
	}
	
	public void testExample(Verify verify) throws Throwable {
		StringBuffer stringBuffer = new StringBuffer();

		MainRunner.create().registerFixture(stringBuffer)
			.run(ExampleTest.class);

		verify.equal("mock testOne mock testTwo ", stringBuffer.toString());
	}

	public static class ExampleTest implements Test {
		private final StringBuffer stringBuffer;

		public ExampleTest(StringBuffer stringBuffer) {
			this.stringBuffer = stringBuffer;
		}

		public void mock() {
			stringBuffer.append("mock ");
		}

		public void testOne() {
			stringBuffer.append("testOne ");
		}
		
		public void testTwo() {
			stringBuffer.append("testTwo ");
		}
	}
}
