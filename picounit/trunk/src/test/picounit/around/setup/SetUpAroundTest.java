package picounit.around.setup;

import picounit.MainRunner;
import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.impl.MethodInvoker;
import picounit.impl.PicoResolver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SetUpAroundTest implements Test {
	// Mocks
	private MethodInvoker methodInvoker;
	private TestInstance testInstance;
	private PicoResolver picoResolver;

	// Unit
	private SetUpAround setUpAround;

	public void mock(MethodInvoker methodInvoker, TestInstance testInstance,
		PicoResolver picoResolver) {

		this.methodInvoker = methodInvoker;
		this.testInstance = testInstance;
		this.picoResolver = picoResolver;
		
		this.setUpAround = new SetUpAroundImpl(methodInvoker, picoResolver);
	}
	
	public void testBeforeInvokesSetUpMethods(Mocker mocker) {
		methodInvoker.invokeMatchingMethods(testInstance, "setUp", picoResolver);
		
		mocker.replay();
		
	 	setUpAround.before(testInstance, TestInstance.testOne);
	}
	
	public void testAfterInvokesTearDownMethods(Mocker mocker) {
	 	methodInvoker.invokeMatchingMethods(testInstance, "tearDown", picoResolver);

		mocker.replay();

		setUpAround.after(testInstance, TestInstance.testOne);
	}
	
	public void testExample(Verify verify)
		throws InstantiationException, IllegalAccessException {

		StringBuffer stringBuffer = new StringBuffer();
		LinkedList linkedList = new LinkedList();
		
		ExampleTest.objects.clear();
		new MainRunner(false)
			.registerFixture(stringBuffer)
			.registerFixture(linkedList)
			.run(ExampleTest.class);

		List expected = Arrays.asList(new Object[] {stringBuffer, "testOne", linkedList,
			stringBuffer, "testTwo", linkedList});

		verify.equal(expected, ExampleTest.objects);
	}

	public static class ExampleTest implements Test {
		public static final List objects = new LinkedList();

		public void setUp(StringBuffer stringBuffer) {
			objects.add(stringBuffer);
		}

		public void tearDown(LinkedList linkedList) {
			objects.add(linkedList);
		}

		public void testOne() {
			objects.add("testOne");
		}
		
		public void testTwo() {
			objects.add("testTwo");			
		}
	}
}
