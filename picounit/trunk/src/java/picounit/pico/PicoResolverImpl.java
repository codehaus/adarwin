package picounit.pico;

import org.picocontainer.PicoContainer;

import picounit.impl.PicoResolver;
import picounit.impl.PicoUnitException;
import picounit.impl.UserPicoResolver;
import picounit.util.Equals;

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
		return equals.equals(this, object);
	}
	
	private final Equals equals = new Equals() {
		protected boolean equalsImpl(Object lhs, Object rhs) {
			PicoResolverImpl left = (PicoResolverImpl) lhs;
			PicoResolverImpl right = (PicoResolverImpl) rhs;
			
			return left.picoContainer.equals(right.picoContainer);
		}
		
	};
}
