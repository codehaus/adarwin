package org.ajester;

import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;


public class IfStatementMutator extends BaseMutator {
	public IfStatementMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
	}

	public void visitJumpInsn(int opcode, Label label) {
		mutateIfMatches(new JumpInstruction(opcode, label));
	}

	public void mutateIfMatches(Instruction instruction) {
		if (matches(instruction)) {
			addCovered();
			instruction = mutate(instruction);
		}
		
		instruction.visit(getClassVisitor(), getCodeVisitor());
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
				return new JumpInstruction(Constants.IFNE, jumpInstruction.getLabel());
			case Constants.IFNE:
				print("IFNE");
				return new JumpInstruction(Constants.IFEQ, jumpInstruction.getLabel());
			default:
				return instruction;
		}
	}
}
