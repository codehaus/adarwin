package picounit.test;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.DefaultPicoContainer;

import picounit.Test;
import picounit.Verify;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PicoTest implements Test {
	public void testParentChild(Verify verify) {
		MutablePicoContainer parent = new DefaultPicoContainer();
		MutablePicoContainer child = new DefaultPicoContainer(parent);
	
		child.registerComponentImplementation(List.class, ArrayList.class);
		parent.registerComponentImplementation(List.class, LinkedList.class);

		Object instance = child.getComponentInstance(List.class);

		verify.that(instance instanceof ArrayList);
	}
	
	public void test3tier(Verify verify) {
		MutablePicoContainer grandParent = new DefaultPicoContainer();
		MutablePicoContainer parent = new DefaultPicoContainer(grandParent);
		MutablePicoContainer child = new DefaultPicoContainer(parent);

		grandParent.registerComponentInstance(new StringBuffer());
		child.registerComponentInstance(new LinkedList());

		verify.that(parent.getComponentInstance(StringBuffer.class) != null);
		verify.that(parent.getComponentInstance(LinkedList.class) == null);
	}
}
