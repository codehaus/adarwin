package picounit.junit;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.around.AroundRunner;
import picounit.impl.MethodInvoker;
import picounit.impl.PicoResolver;
import picounit.impl.Registry;
import picounit.suite.SuiteInstance;

public class JUnitRunnerTest implements Test {
	private JUnitRunner junitRunner;

	// Mocks
	private PicoResolver picoResolver;
	private AroundRunner aroundRunner;
	private MethodInvoker methodInvoker;
	private TestResultProxy testResultProxy;
	private junit.framework.Test test;
	private JUnitListener junitListener;
	private SuiteInstance suiteOneInstance;
	private Registry registry;

	public void mock(PicoResolver picoResolver, AroundRunner aroundRunner,
		MethodInvoker methodInvoker, junit.framework.Test test, TestResultProxy testResultProxy,
		JUnitListener junitListener, SuiteInstance suiteOneInstance, Registry registry) {

		this.junitRunner = new JUnitRunnerImpl(picoResolver, aroundRunner, methodInvoker,
			junitListener, registry);
		
		this.picoResolver = picoResolver;
		this.aroundRunner = aroundRunner;
		this.junitListener = junitListener;
		this.suiteOneInstance = suiteOneInstance;
		this.registry = registry;
		this.methodInvoker = methodInvoker;
		this.test = test;
		this.testResultProxy = testResultProxy;
	}
	
	public void testEnterAndExitSuite(Mocker mocker) {
		mocker.expectAndReturn(picoResolver.getComponent(SuiteInstance.class), suiteOneInstance);
		aroundRunner.before(suiteOneInstance, SuiteInstance.suiteOne);
		aroundRunner.after(suiteOneInstance, SuiteInstance.suiteOne);
		
		mocker.replay();
		
		junitRunner.enterSuite(SuiteInstance.suiteOne);
		junitRunner.exitSuite();
	}

	public void testNoContext(Mocker mocker) {
		junitListener.setContext(test, testResultProxy);
		registry.push();
		registry.registerFixture(TestInstance.class);
		methodInvoker.invokeMethod(TestInstance.testOne, junitListener);
		registry.pop();

		mocker.replay();

		junitRunner.runTest(TestInstance.testOne, test, testResultProxy);
	}
}
