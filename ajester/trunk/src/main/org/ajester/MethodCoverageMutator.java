package org.ajester;

import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;

public class MethodCoverageMutator extends BaseMutator {
	private static final String COVERAGE_CLASS = Coverage.class.getName().replace('.', '/');

	public MethodCoverageMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
	}

	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions) {
		CodeVisitor codeVisitor = super.visitMethod(access, name, desc, exceptions);

		if (matches(new MethodInstruction(access, name, desc, exceptions))) {
			addMethodCoverageCall(codeVisitor, getCurrentClassName(), name);
			addCovered();
		}

		return codeVisitor;
	}
	
	public Instruction mutate(Instruction instruction) {
		return instruction;
	}

	public void addMethodCoverageCall(CodeVisitor codeVisitor, String className,
		String methodName) {

		codeVisitor.visitLdcInsn(className);
		codeVisitor.visitLdcInsn(methodName);
		codeVisitor.visitMethodInsn(Constants.INVOKESTATIC, COVERAGE_CLASS,
			Coverage.METHOD_COVERED, "(Ljava/lang/String;Ljava/lang/String;)V");
	}

	public boolean matches(Instruction instructionType) {
		if (instructionType instanceof MethodInstruction) {
			MethodInstruction methodInstruction =
				(MethodInstruction) instructionType;
			
			return !methodInstruction.getMethodName().equals("<init>") &&
				!methodInstruction.getMethodName().equals("<clinit>");
		}

		return false;
	}
}
