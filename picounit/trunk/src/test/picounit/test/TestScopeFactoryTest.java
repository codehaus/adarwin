package picounit.test;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.impl.ResultListener;
import picounit.impl.Scope;
import picounit.impl.ScopeImpl;

public class TestScopeFactoryTest implements Test {
	// Unit
	private TestScopeFactory testScopeFactory;
	
	// Mocks
	private ResultListener resultListener;
	
	// Fixtures
	private Class testClass = TestInstance.class;
	private Scope testScope = new ScopeImpl(Test.TEST, Test.class, testClass);

	public void mock(ResultListener resultListener) {
		this.resultListener = resultListener;

		this.testScopeFactory = new TestScopeFactoryImpl(resultListener);
	}

	public void testCreateClassScope(Mocker mocker) {
		resultListener.enter(testScope);

		mocker.replay();

		testScopeFactory.enterClass(testClass);
	}
	
	public void testExit(Mocker mocker) {
		resultListener.exit();
		
		mocker.replay();
		
		testScopeFactory.exit();
	}
}
