package org.ajester;

import org.ajester.testmodel.code.IfStatement;
import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;

import junit.framework.TestCase;

public class IfStatementMutatorTestCase extends TestCase {
	private IfStatementMutator mutator;

	protected void setUp() throws Exception {
		super.setUp();

		mutator = new IfStatementMutator(IfStatement.IF_EQUAL_LOCATION);
	}

	public void testMutatesMatchingMethod() {
		Label label = new Label();
		
		JumpInstruction jumpInstruction =
			new JumpInstruction(IfStatement.IF_EQUAL_LOCATION, Constants.IFEQ, label);
		JumpInstruction expectedMutatedJumpInstruction =
			new JumpInstruction(IfStatement.IF_EQUAL_LOCATION, Constants.IFNE, label);
		
		assertTrue(mutator.matches(jumpInstruction));
		assertEquals(expectedMutatedJumpInstruction, mutator.mutate(jumpInstruction));
	}
}
