package org.ajester;

import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;


public class IfStatementClassAdapter extends MutatingClassAdapter {
	private String methodToMutate;

	public IfStatementClassAdapter(String classToMutate, String methodToMutate) {
		super(classToMutate);
		
		this.methodToMutate = methodToMutate;
	}
	
	public void visitJumpInsn(int opcode, Label label) {
		if (getMethodName().equals(methodToMutate)) {
			switch (opcode) {
				case Constants.IFEQ:
					opcode = Constants.IFNE;
					break;
				case Constants.IFNE:
					opcode = Constants.IFEQ;
					break;
			}
		}
		
		super.visitJumpInsn(opcode, label);
	}

}
