package org.ajester;

import org.objectweb.asm.Constants;

public class BooleanReturnClassAdapter extends MutatingClassAdapter {
	public BooleanReturnClassAdapter(String classToMutate) {
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
