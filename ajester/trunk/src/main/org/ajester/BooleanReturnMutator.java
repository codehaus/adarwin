package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnMutator extends BaseMutator {
	private final CodeMatcher codeMatcher;

	public BooleanReturnMutator(CodeMatcher codeMatcher) {
		this.codeMatcher = codeMatcher;
	}

	public boolean matches(Instruction instruction) {
		if (instruction instanceof OrdinaryInstruction) {
			OrdinaryInstruction ordinaryInstruction = (OrdinaryInstruction) instruction;

			return !getMethodName().equals("<init>") && !getMethodName().equals("<clinit>") &&
				ordinaryInstruction.getOpcode() == Constants.ICONST_1;
		}
		
		return false;
	}

	public Instruction mutate(Instruction instruction) {
		return new OrdinaryInstruction(Constants.ICONST_0);
	}

	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
}
