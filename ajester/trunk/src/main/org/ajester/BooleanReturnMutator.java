package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnMutator extends Mutator {
	public BooleanReturnMutator(CodeLocation codeLocation) {
		super(codeLocation);
	}

	public void visitInsn(int opcode) {
		if (opcode == Constants.ICONST_1) {
			super.visitInsn(Constants.ICONST_0);
			return;
		}

		super.visitInsn(opcode);
	}
}
