package org.ajester;

import org.objectweb.asm.Constants;

public class IfStatementInstructionMutator extends AbstractInstructionMutator {
	public IfStatementInstructionMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
	}

	public boolean matches(Instruction instructionType) {
		if (instructionType instanceof JumpInstruction) {
			JumpInstruction jumpInstruction =
			(JumpInstruction) instructionType;
			
			return jumpInstruction.getOpcode() == Constants.IFEQ ||
			jumpInstruction.getOpcode() == Constants.IFNE;			
		}
		
		return false;
	}
	
	public Instruction mutateImpl(Instruction instruction) {
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