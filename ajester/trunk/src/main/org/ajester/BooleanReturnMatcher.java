package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnMatcher implements InstructionMatcher {
	private final CodeMatcher codeMatcher;
	
	public BooleanReturnMatcher(CodeMatcher codeMatcher) {
		this.codeMatcher = codeMatcher;
	}
	
	public boolean matches(Instruction instruction) {
		if (instruction instanceof OrdinaryInstruction) {
			OrdinaryInstruction ordinaryInstruction = (OrdinaryInstruction) instruction;
			String methodName = ordinaryInstruction.getCodeLocation().getMethodName();

			return !methodName.equals("<init>") && !methodName.equals("<clinit>") &&
			ordinaryInstruction.getOpcode() == Constants.ICONST_1;
		}
		
		return false;
	}

	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
}