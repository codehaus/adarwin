package org.ajester;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;

import java.util.HashSet;
import java.util.Set;

abstract class BaseMutator extends ClassAdapter implements Mutator {
	private final CodeMatcher codeMatcher;
	private final Set mutations;
	private CodeVisitor codeVisitor;
	private String currentClassName;
	private String currentMethodName;
	
	public BaseMutator(CodeMatcher codeMatcher) {
		super(new ReuseableClassWriter());
		this.codeMatcher = codeMatcher;
		this.mutations = new HashSet();
	}

	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
	
	public Set getMutations() {
		return mutations;
	}

	public ReuseableClassWriter getClassWriter() {
		return (ReuseableClassWriter) getClassVisitor();
	}

	public CodeVisitor visitMethod(final int access, final String methodName, final String desc,
		final String[] exceptions) {

		setMethodName(methodName);
		print("visitMethod(" + methodName + ")");
		setCodeVisitor(cv.visitMethod(access, methodName, desc, exceptions));

		if (getCodeMatcher().matches(
			new CodeLocation(getCurrentClassNameWithDots(), getMethodName()))) {

			MethodCoverageMutator mutator = new MethodCoverageMutator(null);
			
			if (mutator.matches(new MethodInstruction(access, methodName, desc, exceptions))) {
				mutator.addMethodCoverageCall(codeVisitor, getCurrentClassName(),
					getMethodName());
			}

			return this;
		}
		else {
			return codeVisitor;
		}
	}

	public void visit(int access, String className, String superName, String[] interfaces,
		String sourceFile) {

		setCurrentClassName(className);
		print("visit(" + className +")");

		super.visit(access, className, superName, interfaces, sourceFile);
	}

	// CodeVisitor methods
	public void visitInsn(final int opcode) {
		print("visitInsn(" + lookup(opcode) + ")");
		codeVisitor.visitInsn(opcode);
	}

	public void visitIntInsn(final int opcode, final int operand) {
		print("visitIntInsn(" + lookup(opcode) + ")");
		codeVisitor.visitIntInsn(opcode, operand);
	}

	public void visitVarInsn(final int opcode, final int var) {
		print("visitVarInsn(" + lookup(opcode) + ")");
		codeVisitor.visitVarInsn(opcode, var);
	}

	public void visitTypeInsn(final int opcode, final String desc) {
		print("visitTypeInsn(" + lookup(opcode) + ")");
		codeVisitor.visitTypeInsn(opcode, desc);
	}

	public void visitFieldInsn(final int opcode, final String owner, final String name,
		final String desc) {

		print("visitFieldInsn(" + lookup(opcode) + ")");
		codeVisitor.visitFieldInsn(opcode, owner, name, desc);
	}

	public void visitMethodInsn(final int opcode, final String owner, final String name,
		final String desc) {

		print("visitMethodInsn(" + lookup(opcode) + ")");
		codeVisitor.visitMethodInsn(opcode, owner, name, desc);
	}

	public void visitJumpInsn(final int opcode, final Label label) {
		print("visitJumpInsn(" + lookup(opcode) + ")");
		
		codeVisitor.visitJumpInsn(opcode, label);
	}

	public void visitLabel(final Label label) {
		print("visitLabel");
		codeVisitor.visitLabel(label);
	}

	public void visitLdcInsn(final Object cst) {
		print("visitLdcInsn(" + cst + ")");
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

		print("visitLocalVariable(" + name + ")");
		codeVisitor.visitLocalVariable(name, desc, start, end, index);
	}

	public void visitLineNumber(final int line, final Label start) {
		print("visitLineNumber(" + line + ")");
		codeVisitor.visitLineNumber(line, start);
	}

	public String getMethodName() {
		return currentMethodName;
	}	

	public void setMethodName(String methodName) {
		currentMethodName = methodName;
	}

	public String getCurrentClassName() {
		return currentClassName;
	}

	protected String getCurrentClassNameWithDots() {
		return getCurrentClassName().replace('/', '.');
	}

	public void setCodeVisitor(CodeVisitor codeVisitor) {
		this.codeVisitor = codeVisitor;
	}

	public void setCurrentClassName(String name) {
		this.currentClassName = name;
	}
	
	protected void print(final String toPrint) {
//		System.out.println(this + "[" + getCurrentClassName() + "]." +
//			toPrint);
	}
	
	private String lookup(int opcode) {
		return new ConstantLookup().getOpcodeName(opcode);
	}

	public byte[] visit(ClassReader reader) {
		getClassWriter().reset();
		reader.accept(this, false);
		return getClassWriter().toByteArray();
	}

	protected ClassVisitor getClassVisitor() {
		return this.cv;
	}
	
	protected CodeVisitor getCodeVisitor() {
		return codeVisitor;
	}

	protected void addCovered() {
		mutations.add(new CodeLocation(getCurrentClassNameWithDots(), getMethodName()));
	}
}