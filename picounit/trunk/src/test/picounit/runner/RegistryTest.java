package picounit.runner;


import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.ConstructorInjectionComponentAdapter;

import picounit.Mocker;
import picounit.Test;
import picounit.around.AroundRunner;
import picounit.around.AroundRunnerImpl;
import picounit.pico.DynamicHeirarchyPicoContainer;
import picounit.test.PicoResolver;

public class RegistryTest implements Test {
	// Mocks
	private Mocker mocker;
	private AroundRunner aroundRunner;
	
	// Unit
	private Registry registry;
	private MutablePicoContainer infrastructureContainer;
	
	public RegistryTest(Mocker mocker) {
		this.mocker = mocker;
	}

	public void mock(PicoResolver picoResolver, AroundRunner aroundRunner,
		MutablePicoContainer infrastructureContainer, DynamicHeirarchyPicoContainer userContainer,
		MutablePicoContainer overridablesContainer) {

		this.aroundRunner = aroundRunner;
		this.infrastructureContainer = infrastructureContainer;

		this.registry = new RegistryImpl(infrastructureContainer, userContainer, overridablesContainer);
	}
	
	public void testRegisteringNonRegistryListener() {
	 	mocker.ignoreReturn(infrastructureContainer.registerComponentImplementation(String.class));

	 	mocker.replay();
		
		registry.registerInfrastructure(String.class);
	}
	
	public void testRegisteringRegistryListener() {
		mocker.ignoreReturn(infrastructureContainer.registerComponentImplementation(AroundRunnerImpl.class));
	 	mocker.expectAndReturn(infrastructureContainer.getComponentInstance(AroundRunnerImpl.class), aroundRunner);

		mocker.replay();

		registry.registerInfrastructure(AroundRunnerImpl.class);
	}

	public void testRegisteringSecondClassAfterRegisteringListenerDispatchesEventToListener() {
		mocker.ignoreReturn(infrastructureContainer.registerComponentImplementation(AroundRunnerImpl.class));
	 	mocker.expectAndReturn(infrastructureContainer.getComponentInstance(AroundRunnerImpl.class), aroundRunner);
	 	mocker.ignoreReturn(infrastructureContainer.registerComponentImplementation(String.class));
	 	aroundRunner.registryEvent(String.class);

		mocker.replay();

		registry.registerInfrastructure(AroundRunnerImpl.class);
		registry.registerInfrastructure(String.class);
	}

	public static class ComponentAdapterWithEqualsMethod
	extends ConstructorInjectionComponentAdapter {

	public ComponentAdapterWithEqualsMethod(Object componentKey,
		Class componentImplementation) {

		super(componentKey, componentImplementation);
	}
	
	public boolean equals(Object object) {
		if (object == null || !(object instanceof ConstructorInjectionComponentAdapter)) {
			return false;
		}

		ConstructorInjectionComponentAdapter other =
			(ConstructorInjectionComponentAdapter) object;
		
		return getComponentKey().equals(other.getComponentKey()) &&
			getComponentImplementation().equals(other.getComponentImplementation());
	}
}

}
