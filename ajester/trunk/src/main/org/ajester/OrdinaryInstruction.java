package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

public class OrdinaryInstruction implements Instruction {
	private int opcode;

	public OrdinaryInstruction(int opcode) {
		this.opcode = opcode;
	}

	public int getOpcode() {
		return opcode;
	}

	public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor) {
		codeVisitor.visitInsn(getOpcode());
	}
}