package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

public class MethodInstruction implements Instruction {
	private final CodeLocation codeLocation;
	private final int access;
	private final String description;
	private final String[] exceptions;

	public MethodInstruction(CodeLocation codeLocation, int access, String desc,
		String[] exceptions) {
		
		this.codeLocation = codeLocation;
		this.access = access;
		this.description = desc;
		this.exceptions = exceptions;
	}
	
	public CodeLocation getCodeLocation() {
		return codeLocation;
	}

	public int getAccess() {
		return access;
	}

	public String getDescription() {
		return description;
	}
	
	public String[] getExceptions() {
		return exceptions;
	}

	public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor) {
		classVisitor.visitMethod(getAccess(), getCodeLocation().getMethodName(),
			getDescription(), getExceptions());
	}
}