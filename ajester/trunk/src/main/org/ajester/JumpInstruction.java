package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;

public class JumpInstruction implements Instruction {
	private CodeLocation codeLocation;
	private int opcode;
	private Label label;

	public JumpInstruction(CodeLocation codeLocation, int opcode, Label label) {
		this.codeLocation = codeLocation;
		this.opcode = opcode;
		this.label = label;
	}
	
	public CodeLocation getCodeLocation() {
		return codeLocation;
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
	
	public boolean equals(Object object) {
		if (object == null || !object.getClass().equals(getClass())) {
			return false;
		}
		
		if (object == this) {
			return true;
		}
		
		JumpInstruction other = (JumpInstruction) object;
		
		return getOpcode() == other.getOpcode() &&
			getLabel().equals(other.getLabel());
	}

	public int hashCode() {
		return opcode ^ label.hashCode();
	}

	public JumpInstruction clone(int newOpcode) {
		return new JumpInstruction(getCodeLocation(), newOpcode, getLabel());
	}
}