package org.ajester;

import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;


public class IfStatementMutator extends Mutator {
	public IfStatementMutator(CodeLocation codeLocation) {
		super(codeLocation);
	}

	public void visitJumpInsn(int opcode, Label label) {
		switch (opcode) {
			case Constants.IFEQ:
				opcode = Constants.IFNE;
				break;
			case Constants.IFNE:
				opcode = Constants.IFEQ;
				break;
		}
		
		super.visitJumpInsn(opcode, label);
	}	
}
