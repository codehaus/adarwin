package picounit.suite;

import picounit.Mocker;
import picounit.Suite;
import picounit.Test;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

public class SuiteScopeFactoryTest implements Test {
	// Unit
	private SuiteScopeFactory suiteScopeFactory;
	
	// Mocks
	private ResultListener resultListener;
	
	// Fixtures
	private Class suiteClass = SuiteInstance.class;
	private ScopeImpl suiteScope = new ScopeImpl(Suite.SUITE, Suite.class, suiteClass);

	public void mock(ResultListener resultListener) {
		this.suiteScopeFactory = new SuiteScopeFactoryImpl(resultListener);

		this.resultListener = resultListener;
	}

	public void testCreateClassScope(Mocker mocker) {
		resultListener.enter(suiteScope);

		mocker.replay();

		suiteScopeFactory.enterClass(suiteClass);
	}
	
	public void testExit(Mocker mocker) {
		resultListener.exit();

		mocker.replay();

		suiteScopeFactory.exit();
	}
}
