package picounit.runner;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.ConstructorInjectionComponentAdapter;

import picounit.impl.EveryThingFilter;
import picounit.impl.Filter;
import picounit.pico.DynamicHeirarchyPicoContainer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RegistryImpl implements Registry {
	private final Collection listeners = new LinkedList();

	private final MutablePicoContainer overridablesContainer;
	private final DynamicHeirarchyPicoContainer userContainer;
	private final MutablePicoContainer infrastructureContainer;

	private Filter filter = new EveryThingFilter();
	private List callStack = new LinkedList();

	public RegistryImpl(MutablePicoContainer infrastructureContainer,
		DynamicHeirarchyPicoContainer userContainer,
		MutablePicoContainer overridablesContainer) {

		this.overridablesContainer = overridablesContainer;
		this.userContainer = userContainer;
		this.infrastructureContainer = infrastructureContainer;
	}

	public void push() {
		userContainer.push();
	}

	public void pop() {
		userContainer.pop();
	}

	public void registerInfrastructure(Class component) {
		infrastructureContainer.registerComponentImplementation(component);
		
		dispatchEvent(component);

		addListener(component);
	}

	public void registerInfrastructure(Class interfaceClass, Class implementationClass) {
		infrastructureContainer.registerComponentImplementation(interfaceClass, implementationClass);

		dispatchEvent(interfaceClass);

		addListener(interfaceClass);
	}
	
	public void registerOverrideable(Class interfaceClass, Class implementationClass) {
		overridablesContainer.registerComponentImplementation(interfaceClass, implementationClass);

		dispatchEvent(interfaceClass);

		addListener(interfaceClass);
	}

	public void registerFixture(Class interfaceClass, Class implementationClass) {
		userContainer.registerComponentImplementation(interfaceClass, implementationClass);
	}
	
	public void registerFixture(Class implementationClass) {
		userContainer.registerComponentImplementation(implementationClass);
	}

	public void registerFixture(Object implementation) {
		userContainer.registerComponentInstance(implementation);
	}
	
	public void registerFixture(Class interfaceClass, Object implementation) {
		userContainer.registerComponentInstance(interfaceClass, implementation);
	}	

	public void registerTest(Class testClass) {
		callStack.add(0, testClass);

		if (filter.passes(callStack)) {
			userContainer.registerComponent(
				new ConstructorInjectionComponentAdapter(testClass, testClass));

			dispatchEvent(testClass);

			userContainer.unregisterComponent(testClass);
		}

		callStack.remove(0);
	}

	private void dispatchEvent(Class component) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext(); ) {
			RegistryListener registryListener = (RegistryListener) iterator.next();

			registryListener.registryEvent(component);
		}
	}

	private void addListener(Class component) {
		if (RegistryListener.class.isAssignableFrom(component)) {
			listeners.add(getComponent(component));	
		}
	}

	private Object getComponent(Class component) {
		return infrastructureContainer.getComponentInstance(component);
	}

	public void applyFilter(Filter filter) {
		this.filter = filter;
	}
}
