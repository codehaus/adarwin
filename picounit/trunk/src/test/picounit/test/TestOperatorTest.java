package picounit.test;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.impl.MethodRunner;
import picounit.impl.Operator;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class TestOperatorTest implements Test {
	// Mocks
	private ResultListener resultListener;
	private MethodRunner methodRunner;
	private TestScopeFactory testScopeFactory;
	
	// Fixtures
	private ScopeImpl testInstanceScope = new ScopeImpl(Test.class, TestInstance.class);
	private ScopeImpl testOneScope = new ScopeImpl(Method.class, TestInstance.testOne);
	private ScopeImpl testTwoScope = new ScopeImpl(Method.class, TestInstance.testTwo);

	// Unit
	private Operator testOperator;

	public void mock(ResultListener resultListener, MethodRunner methodRunner,
		TestScopeFactory testScopeFactory) {

		this.resultListener = resultListener;
		this.methodRunner = methodRunner;
		this.testScopeFactory = testScopeFactory;
		
		this.testOperator = new TestOperatorImpl(resultListener, methodRunner, testScopeFactory);
	}

	public void testCallsAllTestMethodsAndPassesDependanciesFromPico(Mocker mocker) {
		mocker.expectAndReturn(testScopeFactory.createClassScope(TestInstance.class), testInstanceScope);

		resultListener.enter(testInstanceScope);

		mocker.expectAndReturn(testScopeFactory.createMethodScope(TestInstance.testOne), testOneScope);
		methodRunner.runWrapped(TestInstance.class, TestInstance.testOne, testOneScope); 

		mocker.expectAndReturn(testScopeFactory.createMethodScope(TestInstance.testTwo), testTwoScope);
		methodRunner.runWrapped(TestInstance.class, TestInstance.testTwo, testTwoScope);

		resultListener.exit();

		mocker.replay();

	 	testOperator.operate(TestInstance.class);
	}

	public void testEquals(Verify verify) {
		verify.equal("Should equal self", testOperator, testOperator);

		verify.notEqual("Should not equal null", testOperator, null);

		verify.notEqual("Should not equal objects of other classes", testOperator, new Object());

		verify.equal("Should equal indentical object", new TestOperatorImpl(resultListener, methodRunner, testScopeFactory),
	 		new TestOperatorImpl(resultListener, methodRunner, testScopeFactory));
	}
}
