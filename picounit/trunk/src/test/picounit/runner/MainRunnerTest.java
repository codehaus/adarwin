package picounit.runner;



import org.picocontainer.MutablePicoContainer;

import picounit.EmptyTest;
import picounit.MainRunner;
import picounit.Mocker;
import picounit.Runner;
import picounit.Suite;
import picounit.Test;
import picounit.Verify;
import picounit.around.AroundMatcher;
import picounit.around.AroundMatcherImpl;
import picounit.around.AroundRunnerImpl;
import picounit.around.context.ContextAround;
import picounit.around.context.ContextAroundImpl;
import picounit.around.mock.MockAround;
import picounit.around.mock.MockAroundImpl;
import picounit.around.mock.MockResolver;
import picounit.around.mock.MockResolverImpl;
import picounit.around.setup.SetUpAround;
import picounit.around.setup.SetUpAroundImpl;
import picounit.impl.Logger;
import picounit.impl.LoggerImpl;
import picounit.impl.MethodInvoker;
import picounit.impl.MethodInvokerImpl;
import picounit.impl.MethodRunner;
import picounit.impl.MethodRunnerImpl;
import picounit.impl.PicoResolver;
import picounit.impl.UserPicoResolver;
import picounit.impl.VerifyImpl;
import picounit.mocker.easymock.MockerImpl;
import picounit.pico.DynamicHeirarchyPicoContainer;
import picounit.pico.PicoResolverImpl;
import picounit.suite.SuiteMatcher;
import picounit.suite.SuiteMatcherImpl;
import picounit.suite.SuiteOperator;
import picounit.suite.SuiteOperatorImpl;
import picounit.suite.SuiteRunner;
import picounit.suite.SuiteScopeFactory;
import picounit.suite.SuiteScopeFactoryImpl;
import picounit.test.TestMatcher;
import picounit.test.TestMatcherImpl;
import picounit.test.TestOperator;
import picounit.test.TestOperatorImpl;
import picounit.test.TestRunner;
import picounit.test.TestScopeFactory;
import picounit.test.TestScopeFactoryImpl;

public class MainRunnerTest implements Test {
	// Mocks
	private final Mocker mocker;

	private MutablePicoContainer overrideableContainer;
	private DynamicHeirarchyPicoContainer userContainer;
	private MutablePicoContainer infrastructureContainer;
	private Registry registry;
	
	// Unit
	private MainRunner suiteRunner;


	public MainRunnerTest(Mocker mocker) {
		this.mocker = mocker;
	}

	public void mock(MutablePicoContainer overrideableContainer,
		DynamicHeirarchyPicoContainer userContainer,
		MutablePicoContainer infrastructureContainer, Registry registry) {

		this.overrideableContainer = overrideableContainer;
		this.userContainer = userContainer;
		this.infrastructureContainer = infrastructureContainer;
		this.registry = registry;

		this.suiteRunner = new MainRunner(overrideableContainer, userContainer,
			infrastructureContainer, registry);
	}

	public void testRunNonSuite() {
		expectInfrastructure();
		expectRegisterTest(EmptyTest.class);
		mocker.ignoreReturn(infrastructureContainer.getComponentInstance(Report.class));

		mocker.replay();

		suiteRunner.run(EmptyTest.class);
	}

	public void testRunSuite() {
		expectInfrastructure();
		expectRegisterTest(SuiteImpl.class);
		mocker.ignoreReturn(infrastructureContainer.getComponentInstance(Report.class));

		mocker.replay();

		suiteRunner.run(SuiteImpl.class);
	}

	public void testAddFixture() {
		expectRegister(Fixture.class, FixtureImpl.class);
		expectInfrastructure();
		expectRegisterTest(TestRequireingNonStandardFixture.class);
		mocker.ignoreReturn(infrastructureContainer.getComponentInstance(Report.class));

		mocker.replay();

		suiteRunner.registerInfrastructure(Fixture.class, FixtureImpl.class)
			.run(TestRequireingNonStandardFixture.class);
	}
	
	private void expectInfrastructure() {
		mocker.ignoreReturn(infrastructureContainer.registerComponentInstance(
			Registry.class, mocker.instanceOf(Registry.class)));
		mocker.ignoreReturn(overrideableContainer.registerComponentInstance(
			Runner.class, mocker.instanceOf(Runner.class)));

		expectRegisterOverrideable(Logger.class, LoggerImpl.class);
		expectRegisterOverrideable(Verify.class, VerifyImpl.class);
		expectRegisterOverrideable(Mocker.class, MockerImpl.class);

		expectRegister(PicoResolver.class, PicoResolverImpl.class);
		expectRegisterFixture(UserPicoResolver.class, mocker.instanceOf(PicoResolver.class)); 
		expectRegister(Report.class, ReportImpl.class);

		expectRegister(MethodInvoker.class, MethodInvokerImpl.class);
		expectRegister(MethodRunner.class, MethodRunnerImpl.class);

		// Runner

		// Around
		expectRegister(AroundMatcher.class, AroundMatcherImpl.class);
		expectRegister(AroundRunnerImpl.class);

		// Suite Runner
		expectRegister(SuiteScopeFactory.class, SuiteScopeFactoryImpl.class);
		expectRegister(SuiteMatcher.class, SuiteMatcherImpl.class);
		expectRegister(SuiteOperator.class, SuiteOperatorImpl.class);
		expectRegister(SuiteRunner.class);

		// Test Runner
		expectRegister(TestScopeFactory.class, TestScopeFactoryImpl.class);
		expectRegister(TestMatcher.class, TestMatcherImpl.class);
		expectRegister(TestOperator.class, TestOperatorImpl.class);
		expectRegister(TestRunner.class);

		// Context Around
		expectRegister(ContextAround.class, ContextAroundImpl.class);
		
		// SetUp Around
		expectRegister(SetUpAround.class, SetUpAroundImpl.class);

		// Mock Around
		expectRegister(MockAround.class, MockAroundImpl.class);
		expectRegister(MockResolver.class, MockResolverImpl.class);
	}

	private void expectRegister(Class interfaceClass, Class implementationClass) {
		registry.registerInfrastructure(interfaceClass, implementationClass);
	}
	
	private void expectRegisterOverrideable(Class interfaceClass, Class implementationClass) {
		registry.registerOverrideable(interfaceClass, implementationClass);
	}

	private void expectRegister(Class implementationClass) {
		registry.registerInfrastructure(implementationClass);
	}
	
	private void expectRegisterFixture(Class interfaceClass, Class implementationClass) {
		registry.registerFixture(interfaceClass, implementationClass);
	}
	
	private void expectRegisterFixture(Class interfaceClass, Object implementation) {
		registry.registerFixture(interfaceClass, implementation);
	}
	
	private void expectRegisterTest(Class testClass) {
		registry.registerTest(testClass);
	}

	public static class SuiteImpl implements Suite {
		public void suite(Runner runner) {
			runner.run(EmptyTest.class);
		}
	}
	
	public interface Fixture {
		boolean truth();
	}
	
	public static class FixtureImpl implements Fixture {
		public boolean truth() {
			return true;
		}
	}

	public static class TestRequireingNonStandardFixture implements Test {
		public void test(Fixture fixture, Verify verify) {
			verify.that(fixture.truth());
		}
	}
}
