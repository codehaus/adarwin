package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

public class OrdinaryInstruction implements Instruction {
	private CodeLocation codeLocation;
	private int opcode;

	public OrdinaryInstruction(CodeLocation codeLocation, int opcode) {
		this.codeLocation = codeLocation;
		this.opcode = opcode;
	}
	
	public CodeLocation getCodeLocation() {
		return codeLocation;
	}

	public int getOpcode() {
		return opcode;
	}

	public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor) {
		codeVisitor.visitInsn(getOpcode());
	}
}