package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

public class MethodInstruction implements Instruction {
	private final String className;
	private final int access;
	private final String methodName;
	private final String description;
	private final String[] exceptions;

	public MethodInstruction(String className, int access, String methodName, String desc,
		String[] exceptions) {
		
		this.className = className;
		this.access = access;
		this.methodName = methodName;
		this.description = desc;
		this.exceptions = exceptions;
	}
	
	public String getCurrentClassName() {
		return className;
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