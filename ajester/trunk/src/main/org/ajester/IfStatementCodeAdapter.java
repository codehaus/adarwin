package org.ajester;

import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;

public class IfStatementCodeAdapter extends MutatingCodeAdapter {
	private ConstantLookup lookup = new ConstantLookup();
	private String methodName;
	
	public IfStatementCodeAdapter(String className, String methodName) {
		super(className);
		this.methodName = methodName;
	}

	public void visitJumpInsn(int opcode, Label label) {
		if (getMethodName().equals(methodName)) {
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
