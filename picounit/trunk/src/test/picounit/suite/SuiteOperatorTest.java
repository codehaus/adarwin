package picounit.suite;

import picounit.Mocker;
import picounit.Suite;
import picounit.Test;
import picounit.Verify;
import picounit.impl.MethodRunner;
import picounit.impl.Operator;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class SuiteOperatorTest implements Test {
	// Mocks
	private ResultListener resultListener;
	private MethodRunner methodRunner;

	// Unit
	private Operator suiteOperator;

	public void mock(ResultListener resultListener, MethodRunner methodRunner) {
		this.resultListener = resultListener;
		this.methodRunner = methodRunner;

		this.suiteOperator = new SuiteOperatorImpl(resultListener, methodRunner);
	}

	public void testCallsAllSuiteMethodsAndPassesDependanciesFromPico(Mocker mocker) {
		resultListener.enter(new ScopeImpl(Suite.class, SuiteInstance.class));
		
		methodRunner.runWrapped(SuiteInstance.class, SuiteInstance.suiteOne, new ScopeImpl(Method.class, SuiteInstance.suiteOne));
		methodRunner.runWrapped(SuiteInstance.class, SuiteInstance.suiteTwo, new ScopeImpl(Method.class, SuiteInstance.suiteTwo));
		
		resultListener.exit();

		mocker.replay();

	 	suiteOperator.operate(SuiteInstance.class);
	}

	public void testEquals(Verify verify) {
		verify.equal("Should equal self", suiteOperator, suiteOperator);

		verify.notEqual("Should not equal null", suiteOperator, null);

		verify.notEqual("Should not equal objects of other classes", suiteOperator, new Object());

		verify.equal("Should equal indentical object", new SuiteOperatorImpl(resultListener, methodRunner),
	 		new SuiteOperatorImpl(resultListener, methodRunner));
	}
}
