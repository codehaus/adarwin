package picounit.impl;

public interface Resolver {
	Object getComponent(Class componentClass);

	Object[] getComponents(Class[] componentClasses);
}
