package org.ajester;

public class MethodCoverageMatcher implements InstructionMatcher {
	private final CodeMatcher codeMatcher;

	public MethodCoverageMatcher(CodeMatcher codeMatcher) {
		this.codeMatcher = codeMatcher;
	}
	
	public boolean matches(Instruction instructionType) {
		if (instructionType instanceof MethodInstruction) {
			MethodInstruction methodInstruction =
			(MethodInstruction) instructionType;

			return !methodInstruction.getCodeLocation().getMethodName().equals("<init>") &&
			!methodInstruction.getCodeLocation().getMethodName().equals("<clinit>");
		}

		return false;
	}

	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
}