package picounit.junit;

import picounit.Suite;
import picounit.Test;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class RecorderImpl implements Recorder {
	private List context = new LinkedList();
	private TestSuite testSuite = new TestSuite();
	private List testSuiteStack = new LinkedList();
	private final JUnitRunner junitRunner;

	public RecorderImpl(JUnitRunner junitRunner) {
		this.junitRunner = junitRunner;
		testSuite.setName("PicoUnit");
	}

	public void record(Event event) {
		if (!sameTest(event)) {
			pushContext(event);
		}

		if (event.getType().equals(Test.class)) {
			addTest(event.getMethod());
		}
	}

	private boolean sameTest(Event event) {
		return lastContext() != null && event.getType().equals(Test.class) &&
			lastContext().getMethod().getDeclaringClass().equals(event.getMethod().getDeclaringClass());
	}

	public void exit() {
		popContext();
	}
	
	public TestSuite getTestSuite() {
		return testSuite;
	}

	private void pushContext(Event event) {
		context.add(event);

		pushSuite(event.getMethod(), event.getType().equals(Suite.class));
	}

	private void addTest(final Method method) {
		final String testSuiteName = testSuite.getName();
		final Collection context = getMethodContext();

		TestCase testCase = new TestCase() {
			public String getName() {
				return method.getName() + "(" + testSuiteName + ")";
			}

			public int countTestCases() {
				return 1;
			}
			
			public void run(TestResult testResult) {
				testResult.startTest(this);

				junitRunner.runTest(method, this, new TestResultProxyImpl(testResult));

				testResult.endTest(this);
			}
		};
		
		testSuite.addTest(testCase);
	}

	private LinkedList getMethodContext() {
		LinkedList methodContext = new LinkedList();
		
		for (Iterator iterator = context.iterator(); iterator.hasNext(); ) {
			Event event = (Event) iterator.next();
	
			methodContext.add(event.getMethod());
		}

		return methodContext;
	}

	private void pushSuite(final Method suiteMethod, final boolean isSuite) {
		TestSuite newTestSuite = new TestSuite() {
			public void run(TestResult result) {
				if (isSuite) {
					junitRunner.enterSuite(suiteMethod);
				}

				super.run(result);

				if (isSuite) {
					junitRunner.exitSuite();
				}
			}
		};
		newTestSuite.setName(suiteMethod.getDeclaringClass().getName());
		testSuite.addTest(newTestSuite);

		if (testSuite != null) {
			testSuiteStack.add(0, testSuite);
		}

		testSuite = newTestSuite;
	}

	private Event lastContext() {
		return context.size() == 0 ? null : (Event) context.get(context.size() - 1);
	}

	private void popContext() {
		if (context.size() > 0) {
			context.remove(context.size() - 1);
			
			popSuite();
		}
	}

	private void popSuite() {
		if (!testSuiteStack.isEmpty()) { 
			testSuite = (TestSuite) testSuiteStack.remove(0);
		}
	}
}
