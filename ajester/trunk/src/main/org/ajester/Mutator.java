package org.ajester;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;

abstract class Mutator extends ClassAdapter implements CodeVisitor {
	private CodeVisitor codeVisitor;
	private String currentClassName;
	private String currentMethodName;
	private CodeLocation codeLocation;

	public Mutator(CodeLocation codeLocation) {
		super(new ClassWriter(true));
		setCodeLocation(codeLocation);
	}

	public void setCodeLocation(CodeLocation codeLocation) {
		this.codeLocation = codeLocation;
	}

	public ClassWriter getClassWriter() {
		return (ClassWriter) this.cv;
	}

	public CodeVisitor visitMethod(final int access, final String name, final String desc, final String[] exceptions) {
		setMethodName(name);
		setCodeVisitor(cv.visitMethod(access, name, desc, exceptions));
		if (codeLocation.shouldMutate(getCurrentClassNameWithDots(), currentMethodName)) {
			return this;
		}
		else {
			return codeVisitor;
		}
	}

	public void visit(int access, String name, String superName, String[] interfaces, String sourceFile) {
		this.currentClassName = name;
		super.visit(access, name, superName, interfaces, sourceFile);
	}

	public boolean shouldMutate(String className) {
		return codeLocation.shouldMutate(className);
	}
	
	// CodeVisitor methods
	
	public void visitInsn(final int opcode) {
		codeVisitor.visitInsn(opcode);
	}

	public void visitIntInsn(final int opcode, final int operand) {
		codeVisitor.visitIntInsn(opcode, operand);
	}

	public void visitVarInsn(final int opcode, final int var) {
		codeVisitor.visitVarInsn(opcode, var);
	}

	public void visitTypeInsn(final int opcode, final String desc) {
		codeVisitor.visitTypeInsn(opcode, desc);
	}

	public void visitFieldInsn(final int opcode, final String owner, final String name,
		final String desc) {
		
		codeVisitor.visitFieldInsn(opcode, owner, name, desc);
	}

	public void visitMethodInsn(final int opcode, final String owner, final String name,
		final String desc) {
		
		codeVisitor.visitMethodInsn(opcode, owner, name, desc);
	}

	public void visitJumpInsn(final int opcode, final Label label) {
		codeVisitor.visitJumpInsn(opcode, label);
	}

	public void visitLabel(final Label label) {
		codeVisitor.visitLabel(label);
	}

	public void visitLdcInsn(final Object cst) {
		codeVisitor.visitLdcInsn(cst);
	}

	public void visitIincInsn(final int var, final int increment) {
		codeVisitor.visitIincInsn(var, increment);
	}

	public void visitTableSwitchInsn(final int min, final int max, final Label dflt,
		final Label labels[]) {

		codeVisitor.visitTableSwitchInsn(min, max, dflt, labels);
	}

	public void visitLookupSwitchInsn(final Label dflt, final int keys[], final Label labels[]) {
		codeVisitor.visitLookupSwitchInsn(dflt, keys, labels);
	}

	public void visitMultiANewArrayInsn(final String desc, final int dims) {
		codeVisitor.visitMultiANewArrayInsn(desc, dims);
	}

	public void visitTryCatchBlock(final Label start, final Label end, final Label handler,
		final String type) {

		codeVisitor.visitTryCatchBlock(start, end, handler, type);
	}

	public void visitMaxs(final int maxStack, final int maxLocals) {
		codeVisitor.visitMaxs(maxStack, maxLocals);
	}

	public void visitLocalVariable(final String name, final String desc, final Label start,
		final Label end, final int index) {

		codeVisitor.visitLocalVariable(name, desc, start, end, index);
	}

	public void visitLineNumber(final int line, final Label start) {
		codeVisitor.visitLineNumber(line, start);
	}

	public String getMethodName() {
		return currentMethodName;
	}	

	void setMethodName(String methodName) {
		currentMethodName = methodName;
	}

	public String getCurrentClassName() {
		return currentClassName;
	}

	private String getCurrentClassNameWithDots() {
		return getCurrentClassName().replace('/', '.');
	}

	void setCodeVisitor(CodeVisitor codeVisitor) {
		this.codeVisitor = codeVisitor;
	}
}

