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
import picounit.impl.Logger;
import picounit.impl.LoggerImpl;
import picounit.impl.MethodInvoker;
import picounit.impl.MethodInvokerImpl;
import picounit.impl.MethodRunner;
import picounit.impl.MethodRunnerImpl;
import picounit.impl.NullResultListener;
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


public class MainRunner {
	private final Runner runner;

	public static Runner create() {
		return new MainRunner().runner;
	}

	public static Runner create(MutablePicoContainer overrideableContainer,
		DynamicHeirarchyPicoContainer userContainer, MutablePicoContainer infrastructureContainer,
		Registry registry, DelegatingResultListener resultListener) {

		return new MainRunner(overrideableContainer, userContainer, infrastructureContainer, registry,
			resultListener).runner;
	}

	protected MainRunner() {
		this.runner = registerInfrastructure();
	}

	public MainRunner(MutablePicoContainer overrideableContainer,
		DynamicHeirarchyPicoContainer userContainer, MutablePicoContainer infrastructureContainer,
		Registry registry, DelegatingResultListener delegatingResultListener) {

		this.runner = registerInfrastructure(infrastructureContainer, overrideableContainer, userContainer, registry,
			delegatingResultListener);
	}
	
	protected final Runner getRunner() {
		return runner;
	}

	private Runner registerInfrastructure() {
		MutablePicoContainer overrideableContainer = new DefaultPicoContainer();
		DynamicHeirarchyPicoContainer userContainer = new DynamicHeirarchyPicoContainerImpl(overrideableContainer); 
		MutablePicoContainer infrastructureContainer = new DefaultPicoContainer(userContainer);

		Registry registry = new RegistryImpl(infrastructureContainer, userContainer, overrideableContainer);

		DelegatingResultListener delegatingResultListener = new ResultListenerImpl(new NullResultListener());

		Runner runner = registerInfrastructure(infrastructureContainer, overrideableContainer, userContainer, registry,
			delegatingResultListener);

		delegatingResultListener.setDelegate((ResultListener) infrastructureContainer.getComponentInstanceOfType(ReportImpl.class));

		return runner;
	}

	private Runner registerInfrastructure(MutablePicoContainer infrastructureContainer,
		MutablePicoContainer overrideableContainer, MutablePicoContainer userContainer,
		Registry registry, DelegatingResultListener delegatingResultListener) {
		
		Runner runner = new RunnerImpl(registry, delegatingResultListener);

		infrastructureContainer.registerComponentInstance(Registry.class, registry);
		infrastructureContainer.registerComponentInstance(ResultListener.class, delegatingResultListener);
		overrideableContainer.registerComponentInstance(Runner.class, runner);

		registry.registerInfrastructure(ReportImpl.class);
		
		registry.registerOverrideable(Logger.class, LoggerImpl.class);
		registry.registerOverrideable(Verify.class, VerifyImpl.class);
		registry.registerOverrideable(Mocker.class, MockerImpl.class);

		registry.registerInfrastructure(PicoResolver.class, PicoResolverImpl.class);
		registry.registerFixture(UserPicoResolver.class, new PicoResolverImpl(userContainer));

		registry.registerInfrastructure(MethodRunner.class, MethodRunnerImpl.class);
		registry.registerInfrastructure(MethodInvoker.class, MethodInvokerImpl.class);

		registry.registerInfrastructure(AroundMatcher.class, AroundMatcherImpl.class);
		registry.registerInfrastructure(AroundRunnerImpl.class);

		registerSuiteRunner(registry);
		registeryTestRunner(registry);

		registry.registerInfrastructure(ContextAround.class, ContextAroundImpl.class);

		registry.registerInfrastructure(SetUpAround.class, SetUpAroundImpl.class);

		registry.registerInfrastructure(MockAround.class, MockAroundImpl.class);
		registry.registerInfrastructure(MockResolver.class, MockResolverImpl.class);
		
		return runner;
	}

	protected void registerSuiteRunner(Registry registry) {
		registry.registerInfrastructure(SuiteScopeFactory.class, SuiteScopeFactoryImpl.class);
		registry.registerInfrastructure(SuiteMatcher.class, SuiteMatcherImpl.class);
		registry.registerInfrastructure(SuiteRunner.class);
	}

	protected void registeryTestRunner(Registry registry) {
		registry.registerInfrastructure(TestScopeFactory.class, TestScopeFactoryImpl.class);
		registry.registerInfrastructure(TestMatcher.class, TestMatcherImpl.class);
		registry.registerInfrastructure(TestRunner.class);
	}
}
