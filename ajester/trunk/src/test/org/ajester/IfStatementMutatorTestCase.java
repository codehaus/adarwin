package org.ajester;

import org.ajester.testmodel.code.IfEqualsStatement;
import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;

import junit.framework.TestCase;

public class IfStatementMutatorTestCase extends TestCase {
	private InstructionMutator mutator;
	private InstructionMatcher matcher;

	protected void setUp() throws Exception {
		super.setUp();

		matcher = new IfStatementMatcher(new CodeLocationMatcher(IfEqualsStatement.LOCATION));
		mutator = new IfStatementInstructionMutator(new CodeLocationMatcher(IfEqualsStatement.LOCATION));
	}

	public void testMutatesMatchingMethod() {
		Label label = new Label();
		
		JumpInstruction jumpInstruction =
			new JumpInstruction(IfEqualsStatement.LOCATION, Constants.IFEQ, label);
		JumpInstruction expectedMutatedJumpInstruction =
			new JumpInstruction(IfEqualsStatement.LOCATION, Constants.IFNE, label);
		
		assertTrue(matcher.matches(jumpInstruction));
		assertEquals(expectedMutatedJumpInstruction, mutator.mutate(jumpInstruction));
	}
}
