package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnCodeAdapter extends MutatingCodeAdapter {
	public BooleanReturnCodeAdapter(String classToMutate) {
		super(classToMutate);
	}
	
	public void visitInsn(int opcode) {
		if (opcode == Constants.ICONST_1) {
			super.visitInsn(Constants.ICONST_0);
			return;
		}

		super.visitInsn(opcode);
	}
}
