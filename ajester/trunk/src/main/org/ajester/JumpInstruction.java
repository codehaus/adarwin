package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;

public class JumpInstruction implements Instruction {
	private int opcode;
	private Label label;

	public JumpInstruction(int opcode, Label label) {
		this.opcode = opcode;
		this.label = label;
	}

	public int getOpcode() {
		return opcode;
	}
	
	public Label getLabel() {
		return label;
	}

	public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor) {
		codeVisitor.visitJumpInsn(getOpcode(), getLabel());
	}
}