package org.ajester;

import org.objectweb.asm.Constants;


public class IfStatementMutator extends BaseMutator {
	private final CodeMatcher codeMatcher;

	public IfStatementMutator(CodeMatcher codeMatcher) {
		this.codeMatcher = codeMatcher;
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

	public Instruction mutate(Instruction instruction) {
		JumpInstruction jumpInstruction = (JumpInstruction) instruction;

		switch (jumpInstruction.getOpcode()) {
			case Constants.IFEQ:
				print("IFEQ");
				return jumpInstruction.clone(Constants.IFNE);
			case Constants.IFNE:
				print("IFNE");
				return jumpInstruction.clone(Constants.IFEQ);
			default:
				return instruction;
		}
	}

	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
}
