package picounit.pico;

import org.picocontainer.MutablePicoContainer;

public interface DynamicHeirarchyPicoContainer extends MutablePicoContainer {
	void push();
	
	void pop();
}
