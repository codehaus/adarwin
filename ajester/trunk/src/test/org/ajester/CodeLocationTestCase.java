package org.ajester;

import junit.framework.TestCase;

public class CodeLocationTestCase extends TestCase {
	private static final String MATCHING_CLASS = "MatchingClass";
	private static final String NON_MATCHING_CLASS = "NonMatchingClass";
	private static final String MATCHING_METHOD = "matchingMethod";
	private static final String NON_MATCHING_METHOD = "nonMatchingMethod";
	
	private CodeLocation locationWithMethod;
	
	protected void setUp() throws Exception {
		super.setUp();
		locationWithMethod = new CodeLocation(MATCHING_CLASS, MATCHING_METHOD);
	}
	
	public void testMatchingClassMatchingMethod() {
		assertTrue(locationWithMethod.shouldMutate(MATCHING_CLASS, MATCHING_METHOD));
	}
	
	public void testMatchingClassNonMatchingMethod() {
		assertFalse(locationWithMethod.shouldMutate(MATCHING_CLASS, NON_MATCHING_METHOD));
	}
	
	public void testMatchingClassNullMethod() {
		assertFalse(locationWithMethod.shouldMutate(MATCHING_CLASS, null));
	}
	
	public void testNonMatchingClassMatchingMethod() {
		assertFalse(locationWithMethod.shouldMutate(NON_MATCHING_CLASS, MATCHING_METHOD));
	}
	
	public void testNonMatchingClassNonMatchingMethod() {
		assertFalse(locationWithMethod.shouldMutate(NON_MATCHING_CLASS, NON_MATCHING_METHOD));
	}
	
	public void testNonMatchingClassNullMethod() {
		assertFalse(locationWithMethod.shouldMutate(NON_MATCHING_CLASS, null));
	}	
}
