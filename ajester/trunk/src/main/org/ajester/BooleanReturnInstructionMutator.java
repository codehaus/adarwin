package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnInstructionMutator extends AbstractInstructionMutator {
	public BooleanReturnInstructionMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
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
	
	public Instruction mutateImpl(Instruction instruction) {
		OrdinaryInstruction ordinaryInstruction = (OrdinaryInstruction) instruction;
		
		return new OrdinaryInstruction(ordinaryInstruction.getCodeLocation(), Constants.ICONST_0);
	}
}