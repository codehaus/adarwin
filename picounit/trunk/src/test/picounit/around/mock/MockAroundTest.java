package picounit.around.mock;

import picounit.Mocker;
import picounit.Runner;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.impl.MethodRunner;

import java.util.LinkedList;
import java.util.List;

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
	
	public void testExample(Mocker mocker, Verify verify, Runner runner) throws Throwable {
		List list = (List) mocker.mock(List.class);

		List objects = new LinkedList();
		
		runner.registerFixture(objects)
			.run(ExampleTest.class);

		Object[] actual = objects.toArray();

		verify.equal(4, actual.length);
		verify.equal(list.getClass(), actual[0].getClass());
		verify.equal("testOne", actual[1]);
		verify.equal(list.getClass(), actual[2].getClass());
		verify.equal("testTwo", actual[3]);
	}

	public static class ExampleTest implements Test {
		private final List objects;

		public ExampleTest(List objects) {
			this.objects = objects;
		}

		public void mock(List list) {
			objects.add(list);
		}

		public void testOne() {
			objects.add("testOne");
		}
		
		public void testTwo() {
			objects.add("testTwo");
		}
	}
}
