package org.ajester;

import org.ajester.testmodel.code.BooleanReturn;

import junit.framework.TestCase;

public class MutatorFactoryTestCase extends TestCase {
	public void testSomething() throws Exception {
		MutatorFactory mutatorFactory = new MutatorFactory(BooleanReturnMutator.class);
		Mutator mutator = mutatorFactory.createMutator(BooleanReturn.LOCATION);
		
		assertNotNull(mutator);
		assertEquals(BooleanReturnMutator.class, mutator.getClass());
//		assertEquals(BooleanReturn.GET_TRUE_LOCATION, mutator.getCodeMatcher());
	}
}
