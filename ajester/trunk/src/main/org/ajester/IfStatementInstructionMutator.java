package org.ajester;

import org.objectweb.asm.Constants;

public class IfStatementInstructionMutator extends IfStatementMatcher implements InstructionMutator {
	public IfStatementInstructionMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
	}

	public Instruction mutate(Instruction instruction) {
		JumpInstruction jumpInstruction = (JumpInstruction) instruction;

		switch (jumpInstruction.getOpcode()) {
			case Constants.IFEQ:
				return jumpInstruction.clone(Constants.IFNE);
			case Constants.IFNE:
				return jumpInstruction.clone(Constants.IFEQ);
			default:
				return instruction;
		}
	}
}