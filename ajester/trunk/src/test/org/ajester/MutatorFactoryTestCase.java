package org.ajester;

import org.ajester.testmodel.code.BooleanReturn;

import junit.framework.TestCase;

public class MutatorFactoryTestCase extends TestCase {
	public void testSomething() throws Exception {
		MutatorFactory mutatorFactory = new MutatorFactory(BooleanReturnInstructionMutator.class);
		InstructionMutator mutator = mutatorFactory.createMutator(
			new CodeLocationMatcher(BooleanReturn.LOCATION));
		
		assertNotNull(mutator);
		assertEquals(BooleanReturnInstructionMutator.class, mutator.getClass());
//		assertEquals(BooleanReturn.GET_TRUE_LOCATION, mutator.getCodeMatcher());
	}
}
