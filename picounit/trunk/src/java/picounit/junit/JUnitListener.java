package picounit.junit;

import picounit.Suite;
import picounit.Test;
import picounit.impl.ResultListener;
import picounit.impl.Scope;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class JUnitListener implements ResultListener {
	private TestSuite testSuite = new TestSuite();
	private List testSuiteStack = new LinkedList();
	
	private List scopeStack = new LinkedList();
	
	private Scope currentScope;

	public JUnitListener() {
		testSuite.setName("PicoUnit Tests");
	}
	
	public void enter(Scope scope) {
		if (!ignore(scope)) {
			print("enter:" + scope);
		}
		pushScope(scope);
		
		if (isTest(scope)) {
			pushTest(scope.value());
//			print("\ntest: " + scope.value());
		}
		else if (isTestMethod(scope)) {
			addTest(scope.value());

//			print("\nmethod: " + scope.value());
		}
	}

	private void print(String string) {
		//System.out.println(string);
	}

	private boolean ignore(Scope scope) {
		return !(isTestMethod(scope) || isTest(scope) || isSuite(scope));
	}

	private boolean isTestMethod(Scope scope) {
		return scope.matches(Test.TEST_METHOD);
	}

	private boolean isTest(Scope scope) {
		return scope.matches(Test.TEST);
	}

	private boolean isSuite(Scope scope) {
		return scope.matches(Suite.SUITE);
	}

	private void pushScope(Scope scope) {
		if (currentScope != null) {
			scopeStack.add(0, currentScope);
		}
		currentScope = scope;
	}
	
	private void popScope() {
		if (!ignore(currentScope)) {
			print("exit: " + currentScope);
		}
		Scope previousScope = currentScope;

		if (!scopeStack.isEmpty()) {
			currentScope = (Scope) scopeStack.remove(0);
		}

		if (isTest(previousScope)) {
			popSuite();
		}
	}

	private void pushSuite(Object object) {
		Class testClass = (Class) object;

		print("pushSuite: " + testClass.getName());
		TestSuite newTestSuite = new TestSuite();
		newTestSuite.setName(testClass.getName());
		testSuite.addTest(newTestSuite);

		if (testSuite != null) {
			testSuiteStack.add(0, testSuite);
		}

		testSuite = newTestSuite;
	}

	private void popSuite() {
		print("popSuite");

		if (!testSuiteStack.isEmpty()) { 
			testSuite = (TestSuite) testSuiteStack.remove(0);
		}
	}

	private void pushTest(Object object) {
		pushSuite(object);
	}

	private void addTest(final Object object) {
		final Method method = (Method) object;
		
		print("add " + object + " to " + testSuite.getName());
		
		final String testSuiteName = testSuite.getName();

		TestCase testCase = new TestCase() {
			public String getName() {
				return method.getName() + "(" + testSuiteName + ")";
			}

			public int countTestCases() {
				return 1;
			}
			
			public void run(TestResult testResult) {
				print("Running: " + getName());
				
				testResult.startTest(this);
				testResult.endTest(this);
			}
		};
		
		testSuite.addTest(testCase);
	}

	public void exit() {
		popScope();
	}

	public void exit(Throwable throwable) {
	}

	public TestSuite getTestSuite() {
		return testSuite;
		//return (TestSuite) testSuiteStack.get(testSuiteStack.size() - 1);
	}
}
