package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnInstructionMutator implements InstructionMutator {
	public Instruction mutate(Instruction instruction) {
		OrdinaryInstruction ordinaryInstruction = (OrdinaryInstruction) instruction;
		
		return new OrdinaryInstruction(ordinaryInstruction.getCodeLocation(), Constants.ICONST_0);
	}
}