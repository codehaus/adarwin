package picounit;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.DefaultPicoContainer;

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
import picounit.impl.DelegatingResultListener;
import picounit.impl.Filter;
import picounit.impl.Logger;
import picounit.impl.LoggerImpl;
import picounit.impl.MethodRunner;
import picounit.impl.MethodRunnerImpl;
import picounit.impl.PicoResolver;
import picounit.impl.Registry;
import picounit.impl.RegistryImpl;
import picounit.impl.ReportImpl;
import picounit.impl.ResultListener;
import picounit.impl.ResultListenerImpl;
import picounit.impl.RunnerImpl;
import picounit.impl.UserPicoResolver;
import picounit.impl.VerifyImpl;
import picounit.mocker.easymock.MockerImpl;
import picounit.pico.DynamicHeirarchyPicoContainer;
import picounit.pico.DynamicHeirarchyPicoContainerImpl;
import picounit.pico.PicoResolverImpl;
import picounit.suite.SuiteMatcher;
import picounit.suite.SuiteMatcherImpl;
import picounit.suite.SuiteRunner;
import picounit.suite.SuiteScopeFactory;
import picounit.suite.SuiteScopeFactoryImpl;
import picounit.test.TestMatcher;
import picounit.test.TestMatcherImpl;
import picounit.test.TestRunner;
import picounit.test.TestScopeFactory;
import picounit.test.TestScopeFactoryImpl;


public class MainRunner implements Runner {
	private final MutablePicoContainer overrideableContainer;
	private final DynamicHeirarchyPicoContainer userContainer;
	private final MutablePicoContainer infrastructureContainer;

	private final Registry registry;
	private DelegatingResultListener delegatingResultListener;
	private Runner runner;

	public static Runner create() {
		return new MainRunner().runner;
	}

	private MainRunner() {
		this.overrideableContainer = new DefaultPicoContainer();
		this.userContainer = new DynamicHeirarchyPicoContainerImpl(overrideableContainer); 
		this.infrastructureContainer = new DefaultPicoContainer(userContainer);
		this.registry = new RegistryImpl(infrastructureContainer, userContainer, overrideableContainer);
		this.delegatingResultListener = new ResultListenerImpl();

		this.delegatingResultListener.setDelegate(new ReportImpl());
		this.runner = new RunnerImpl(this);

		registerInfrastructureImpl();
	}

	public MainRunner(MutablePicoContainer overrideableContainer,
		DynamicHeirarchyPicoContainer userContainer, MutablePicoContainer infrastructureContainer,
		Registry registry, DelegatingResultListener resultListener) {

		this.overrideableContainer = overrideableContainer;
		this.userContainer = userContainer;
		this.infrastructureContainer = infrastructureContainer;
		this.registry = registry;
		this.delegatingResultListener = resultListener;
		this.runner = new RunnerImpl(this);

		registerInfrastructureImpl();
	}

	public void print() {
		System.out.println(delegatingResultListener.getDelegate());
	}

	public Runner applyFilter(Filter filter) {
		registry.applyFilter(filter);

		return this;
	}

	public MainRunner registerInfrastructure(Class implementationClass) {
		registry.registerInfrastructure(implementationClass);

		return this;
	}

	public MainRunner registerInfrastructure(Class interfaceClass, Class implementationClass) {
		registry.registerInfrastructure(interfaceClass, implementationClass);

		return this;
	}

	public MainRunner registerInfrastructure(Object object) {
		infrastructureContainer.registerComponentInstance(object);

		return this;
	}

	public Runner registerFixture(Class interfaceClass, Class implementationClass) {
		registry.registerFixture(interfaceClass, implementationClass);

		return this;
	}

	public Runner registerFixture(Class implementationClass) {
		registry.registerFixture(implementationClass);

		return this;
	}

	public Runner registerFixture(Class interfaceClass, Object implementation) {
		registry.registerFixture(interfaceClass, implementation);

		return this;
	}

	public Runner registerFixture(Object implementation) {
		registry.registerFixture(implementation);

		return this;
	}

	public Runner run(Class someClass, ResultListener resultListener) {
		ResultListener previous = delegatingResultListener.setDelegate(resultListener);

		runImpl(someClass);

		delegatingResultListener.setDelegate(previous);
		
		return this;
	}

	public Runner run(Class someClass) {
		runImpl(someClass);

		return this;
	}

	private void runImpl(Class someClass) {
		registry.registerTest(someClass);
	}

	private void registerInfrastructureImpl() {
		infrastructureContainer.registerComponentInstance(Registry.class, registry);
		infrastructureContainer.registerComponentInstance(ResultListener.class, delegatingResultListener);
		overrideableContainer.registerComponentInstance(Runner.class, runner);

		registerOverrideable(Logger.class, LoggerImpl.class);
		registerOverrideable(Verify.class, VerifyImpl.class);
		registerOverrideable(Mocker.class, MockerImpl.class);

		registerInfrastructure(PicoResolver.class, PicoResolverImpl.class);
		registerFixture(UserPicoResolver.class, new PicoResolverImpl(userContainer));

		registerInfrastructure(MethodRunner.class, MethodRunnerImpl.class);

		// Around
		registerInfrastructure(AroundMatcher.class, AroundMatcherImpl.class);
		registerInfrastructure(AroundRunnerImpl.class);

		// Suite Runner
		registerInfrastructure(SuiteScopeFactory.class, SuiteScopeFactoryImpl.class);
		registerInfrastructure(SuiteMatcher.class, SuiteMatcherImpl.class);
		registerInfrastructure(SuiteRunner.class);

		// Test Runner
		registerInfrastructure(TestScopeFactory.class, TestScopeFactoryImpl.class);
		registerInfrastructure(TestMatcher.class, TestMatcherImpl.class);
		registerInfrastructure(TestRunner.class);

		// ContextAround
		registerInfrastructure(ContextAround.class, ContextAroundImpl.class);

		// SetUp Around
		registerInfrastructure(SetUpAround.class, SetUpAroundImpl.class);

		// Mock Around
		registerInfrastructure(MockAround.class, MockAroundImpl.class);
		registerInfrastructure(MockResolver.class, MockResolverImpl.class);
	}

	private void registerOverrideable(Class interfaceClass, Class implementationClass) {
		registry.registerOverrideable(interfaceClass, implementationClass);
	}
}
