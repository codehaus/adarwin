package org.ajester;

import org.objectweb.asm.Constants;

public class IfStatementMatcher implements InstructionMatcher {
	private CodeMatcher codeMatcher;

	public IfStatementMatcher(CodeMatcher codeMatcher) {
		this.codeMatcher = codeMatcher;
	}
	
	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
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
}