package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;

public class MethodCoverageMutator extends BaseMutator {
	private static final String COVERAGE_CLASS = Coverage.class.getName().replace('.', '/');

	public MethodCoverageMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
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

	public Instruction mutate(Instruction instruction) {
		final MethodInstruction methodInstruction = (MethodInstruction) instruction;
		
		return new Instruction() {
			public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor) {
				codeVisitor.visitLdcInsn(methodInstruction.getCurrentClassName());
				codeVisitor.visitLdcInsn(methodInstruction.getMethodName());
				codeVisitor.visitMethodInsn(Constants.INVOKESTATIC, COVERAGE_CLASS,
						Coverage.METHOD_COVERED, "(Ljava/lang/String;Ljava/lang/String;)V");
			}
		};
	}
}
