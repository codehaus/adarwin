package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

public class MethodInstruction implements Instruction {
	private int access;
	private String methodName;
	private String description;
	private String[] exceptions;

	public MethodInstruction(int access, String methodName, String desc, String[] exceptions) {
		this.access = access;
		this.methodName = methodName;
		this.description = desc;
		this.exceptions = exceptions;
	}
	
	public int getAccess() {
		return access;
	}

	public String getMethodName() {
		return methodName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String[] getExceptions() {
		return exceptions;
	}

	public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor) {
		classVisitor.visitMethod(getAccess(), getMethodName(), getDescription(), getExceptions());
	}
}