package org.adarwin;

import java.io.IOException;

import org.adarwin.rule.Rule;
import org.adarwin.testmodel.HasNoZeroArgConstructor;
import org.adarwin.testmodel.HasZeroAndNonZeroArgConstructors;
import org.adarwin.testmodel.HasZeroArgConstructor;

import junit.framework.TestCase;

public class ZeroArgConstructorTestCase extends TestCase {
	private Rule rule;
	
	protected void setUp() throws Exception {
		super.setUp();
		
//		rule = new ZeroArgConstructorRule();
	}
	
	public void testFlibble() {
		
	}
//
//	public void testHasZeroArgConstructor() throws IOException {
//	  assertEquals(1, new ClassFile(HasZeroArgConstructor.class).evaluate(rule).getCount());
//	}
//
//	public void testHasZeroAndNonZeroArgConstructors() throws IOException {
//	  assertEquals(1, new ClassFile(HasZeroAndNonZeroArgConstructors.class).evaluate(rule).getCount());
//	}
//
//	public void testHasNoZeroArgConstructor() throws IOException {
//	  assertEquals(0, new ClassFile(HasNoZeroArgConstructor.class).evaluate(rule).getCount());
//	}
}
