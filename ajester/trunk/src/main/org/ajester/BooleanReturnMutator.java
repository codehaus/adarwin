package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnMutator extends BaseMutator {
	public BooleanReturnMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
	}

	public void visitInsn(int opcode) {
		Instruction instruction = new OrdinaryInstruction(opcode);
		if (matches(instruction)) {
			addCovered();
			instruction = mutate(instruction);
		}
		
		instruction.visit(getClassVisitor(), getCodeVisitor());
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
}
