package picounit.pico;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoRegistrationException;
import org.picocontainer.PicoVerificationException;
import org.picocontainer.defaults.DefaultPicoContainer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DynamicHeirarchyPicoContainerImpl implements DynamicHeirarchyPicoContainer {
	private List heirarchy = new LinkedList();
	private MutablePicoContainer delegate;

	public DynamicHeirarchyPicoContainerImpl(MutablePicoContainer parent) {
		this.delegate = parent;

		push();
	}

	public void push() {
		heirarchy.add(0, delegate);
		delegate = new DefaultPicoContainer(delegate);
	}

	public void pop() {
		delegate = (MutablePicoContainer) heirarchy.remove(0);
	}

	public void setParent(PicoContainer parent) {
		this.delegate.setParent(parent);
	}
	
	public ComponentAdapter registerComponentImplementation(Object componentKey, Class implementationClass) throws PicoRegistrationException {
//		System.err.println("Dyn.regCompImpl(" + componentKey + ", " + implementationClass + ")");
		return delegate.registerComponentImplementation(componentKey, implementationClass);
	}

	public ComponentAdapter registerComponentImplementation(Object componentKey, Class implementationClass, Parameter[] parameters) throws PicoRegistrationException {
		return delegate.registerComponentImplementation(componentKey, implementationClass, parameters);
	}

	public ComponentAdapter registerComponentImplementation(Class implementationClass) throws PicoRegistrationException {
		return delegate.registerComponentImplementation(implementationClass);
	}

	public ComponentAdapter registerComponentInstance(Object implementation) throws PicoRegistrationException {
		return delegate.registerComponentInstance(implementation);
	}

	public ComponentAdapter registerComponentInstance(Object componentKey, Object instance) throws PicoRegistrationException {
		return delegate.registerComponentInstance(componentKey, instance);
	}

	public ComponentAdapter registerComponent(ComponentAdapter adapter) throws PicoRegistrationException {
//		System.err.println("Dyn.regComp(" + adapter + ")");
		return delegate.registerComponent(adapter);
	}

	public ComponentAdapter unregisterComponent(Object componentKey) {
		return delegate.unregisterComponent(componentKey);
	}

	public ComponentAdapter unregisterComponentByInstance(Object instance) {
		return delegate.unregisterComponentByInstance(instance);
	}

	public Object getComponentInstance(Object componentKey) {
//		System.err.println("Dyn.getCompIns(" + componentKey + ")");
		return delegate.getComponentInstance(componentKey);
	}

	public Object getComponentInstanceOfType(Class intefaceClass) {
		return delegate.getComponentInstanceOfType(intefaceClass);
	}

	public List getComponentInstances() {
		return delegate.getComponentInstances();
	}

	public PicoContainer getParent() {
		return delegate.getParent();
	}

	public ComponentAdapter getComponentAdapter(Object componentKey) {
		return delegate.getComponentAdapter(componentKey);
	}

	public ComponentAdapter getComponentAdapterOfType(Class interfaceClass) {
		return delegate.getComponentAdapterOfType(interfaceClass);
	}

	public Collection getComponentAdapters() {
		return delegate.getComponentAdapters();
	}

	public List getComponentAdaptersOfType(Class interfaceClass) {
		return delegate.getComponentAdaptersOfType(interfaceClass);
	}

	public void verify() throws PicoVerificationException {
		delegate.verify();
	}

	public void addOrderedComponentAdapter(ComponentAdapter componentAdapter) {
		delegate.addOrderedComponentAdapter(componentAdapter);
	}

	public void start() {
		delegate.start();
	}

	public void stop() {
		delegate.stop();
	}

	public void dispose() {
		delegate.dispose();
	}
}
