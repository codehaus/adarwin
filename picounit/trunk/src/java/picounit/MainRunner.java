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
import picounit.impl.Filter;
import picounit.impl.Logger;
import picounit.impl.LoggerImpl;
import picounit.impl.MethodInvoker;
import picounit.impl.MethodInvokerImpl;
import picounit.impl.MethodRunner;
import picounit.impl.MethodRunnerImpl;
import picounit.impl.PicoResolver;
import picounit.impl.Registry;
import picounit.impl.RegistryImpl;
import picounit.impl.UserPicoResolver;
import picounit.impl.VerifyImpl;
import picounit.mocker.easymock.MockerImpl;
import picounit.pico.DynamicHeirarchyPicoContainer;
import picounit.pico.DynamicHeirarchyPicoContainerImpl;
import picounit.pico.PicoResolverImpl;
import picounit.runner.Report;
import picounit.runner.ReportImpl;
import picounit.runner.RunnerImpl;
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


public class MainRunner implements Runner {
	private final MutablePicoContainer overrideableContainer;
	private final DynamicHeirarchyPicoContainer userContainer;
	private final MutablePicoContainer infrastructureContainer;

	private final Registry registry;

	private boolean infrastructureRegistered;
	private boolean printReport;

	public MainRunner() {
		this(true);
	}
		
	public MainRunner(boolean printReport) {
		this.printReport = printReport;
		this.overrideableContainer = new DefaultPicoContainer();
		this.userContainer = new DynamicHeirarchyPicoContainerImpl(overrideableContainer); 
		this.infrastructureContainer = new DefaultPicoContainer(userContainer);
		this.registry = new RegistryImpl(infrastructureContainer, userContainer, overrideableContainer);
	}

	public MainRunner(MutablePicoContainer overrideableContainer,
		DynamicHeirarchyPicoContainer userContainer, MutablePicoContainer infrastructureContainer,
		Registry registry) {

		this.printReport = false;
		this.overrideableContainer = overrideableContainer;
		this.userContainer = userContainer;
		this.infrastructureContainer = infrastructureContainer;
		this.registry = registry;
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

	public void run(Class testClass) {
		registerInfrastructureImpl();

		registry.registerTest(testClass);

		if (printReport) {
			System.out.println(infrastructureContainer.getComponentInstance(Report.class));
		}
	}

	private void registerInfrastructureImpl() {
		if (!infrastructureRegistered) {
			infrastructureContainer.registerComponentInstance(Registry.class, registry);
			overrideableContainer.registerComponentInstance(Runner.class, new RunnerImpl(registry));

			registerOverrideable(Logger.class, LoggerImpl.class);
			registerOverrideable(Verify.class, VerifyImpl.class);
			registerOverrideable(Mocker.class, MockerImpl.class);

			registerInfrastructure(PicoResolver.class, PicoResolverImpl.class);
			registerFixture(UserPicoResolver.class, new PicoResolverImpl(userContainer));

			registerInfrastructure(Report.class, ReportImpl.class);
			registerInfrastructure(MethodInvoker.class, MethodInvokerImpl.class);
			registerInfrastructure(MethodRunner.class, MethodRunnerImpl.class);
	
			// Around
			registerInfrastructure(AroundMatcher.class, AroundMatcherImpl.class);
			registerInfrastructure(AroundRunnerImpl.class);

			// Suite Runner
			registerInfrastructure(SuiteScopeFactory.class, SuiteScopeFactoryImpl.class);
			registerInfrastructure(SuiteMatcher.class, SuiteMatcherImpl.class);
			registerInfrastructure(SuiteOperator.class, SuiteOperatorImpl.class);
			registerInfrastructure(SuiteRunner.class);

			// Test Runner
			registerInfrastructure(TestScopeFactory.class, TestScopeFactoryImpl.class);
			registerInfrastructure(TestMatcher.class, TestMatcherImpl.class);
			registerInfrastructure(TestOperator.class, TestOperatorImpl.class);
			registerInfrastructure(TestRunner.class);

			// ContextAround
			registerInfrastructure(ContextAround.class, ContextAroundImpl.class);

			// SetUp Around
			registerInfrastructure(SetUpAround.class, SetUpAroundImpl.class);
	
			// Mock Around
			registerInfrastructure(MockAround.class, MockAroundImpl.class);
			registerInfrastructure(MockResolver.class, MockResolverImpl.class);
	
			infrastructureRegistered = true;
		}
	}

	private void registerOverrideable(Class interfaceClass, Class implementationClass) {
		registry.registerOverrideable(interfaceClass, implementationClass);
	}

	public Object get(Class interfaceClass) {
		return infrastructureContainer.getComponentInstance(interfaceClass);
	}
}
