package picounit.junit;

import picounit.MainRunner;
import picounit.impl.Registry;
import picounit.suite.OrdinarySuiteScopeFactory;
import picounit.suite.SuiteMatcher;
import picounit.suite.SuiteMatcherImpl;
import picounit.suite.SuiteRunner;
import picounit.suite.SuiteScopeFactory;
import picounit.suite.SuiteScopeFactoryImpl;
import picounit.test.TestMatcher;
import picounit.test.TestMatcherImpl;
import picounit.test.TestRunner;
import picounit.test.TestScopeFactory;
import junit.framework.TestSuite;

public class SuiteGenerator extends MainRunner {
	private Registry registry;

	public TestSuite generate(Class testClass) {
		getRunner().run(testClass);

		return getRecorder().getTestSuite();
	}

	protected void registerSuiteRunner(Registry registry) {
		this.registry = registry;
		
		registry.registerInfrastructure(Recorder.class, RecorderImpl.class);
		registry.registerInfrastructure(JUnitRunner.class, JUnitRunnerImpl.class);
		registry.registerInfrastructure(JUnitListener.class, JUnitListenerImpl.class);
		registry.registerInfrastructure(OrdinarySuiteScopeFactory.class, SuiteScopeFactoryImpl.class);

		registry.registerInfrastructure(SuiteScopeFactory.class, RecordingSuiteScopeFactoryImpl.class);
		registry.registerInfrastructure(SuiteMatcher.class, SuiteMatcherImpl.class);
		registry.registerInfrastructure(SuiteRunner.class);
	}

	protected void registeryTestRunner(Registry registry) {
		registry.registerInfrastructure(TestScopeFactory.class, RecordingTestScopeFactoryImpl.class);
		registry.registerInfrastructure(TestMatcher.class, TestMatcherImpl.class);
		registry.registerInfrastructure(TestRunner.class);
	}

	private RecorderImpl getRecorder() {
		return (RecorderImpl) registry.getComponentInstance(Recorder.class);
	}
}
