package picounit.test;

import org.picocontainer.PicoContainer;

import picounit.suite.PicoUnitException;
import picounit.suite.UserPicoResolver;

public class PicoResolverImpl implements PicoResolver, UserPicoResolver {
	private final PicoContainer picoContainer;

	public PicoResolverImpl(PicoContainer picoContainer) {
		if (picoContainer == null) {
			throw new NullPointerException("What ? ");
		}
		
		this.picoContainer = picoContainer;
	}

	public Object getComponent(Class componentClass) {
		try {
			return picoContainer.getComponentInstance(componentClass);
		}
		catch (RuntimeException runtimeException) {
			throw new PicoUnitException(runtimeException);
		}
	}

	public Object[] getComponents(Class[] componentClasses) {
		Object[] componenets = new Object[componentClasses.length];

		for (int index = 0; index < componenets.length; index++) {
			componenets[index] = getComponent(componentClasses[index]);
		}

		return componenets;
	}
	
	public int hashCode() {
		return getClass().hashCode();
	}
	
	public boolean equals(Object object) {
		return new Equals(this) {
			protected boolean equalsImpl(Object object) {
				PicoResolverImpl other = (PicoResolverImpl) object;

				return picoContainer.equals(other.picoContainer);
			}
		}.equals(object);
	}
}
