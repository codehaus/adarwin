package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnInstructionMutator implements InstructionMatcher, InstructionMutator {
	private CodeMatcher codeMatcher;

	public BooleanReturnInstructionMutator(CodeMatcher codeMatcher) {
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
	
	public Instruction mutate(Instruction instruction) {
		OrdinaryInstruction ordinaryInstruction = (OrdinaryInstruction) instruction;
		
		return new OrdinaryInstruction(ordinaryInstruction.getCodeLocation(), Constants.ICONST_0);
	}
	
	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
}