package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;

public class MethodCoverageInstructionMutator extends MethodCoverageMatcher implements InstructionMutator {
	public MethodCoverageInstructionMutator(CodeMatcher codeMatcher) {
		super(codeMatcher);
	}

	private static final String COVERAGE_CLASS = Coverage.class.getName().replace('.', '/');

	public Instruction mutate(Instruction instruction) {
		final MethodInstruction methodInstruction = (MethodInstruction) instruction;
		
		return new Instruction() {
			public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor) {
				codeVisitor.visitLdcInsn(methodInstruction.getCodeLocation().getClassName());
				codeVisitor.visitLdcInsn(methodInstruction.getCodeLocation().getMethodName());
				codeVisitor.visitMethodInsn(Constants.INVOKESTATIC,
					MethodCoverageInstructionMutator.COVERAGE_CLASS, Coverage.METHOD_COVERED,
					"(Ljava/lang/String;Ljava/lang/String;)V");
			}
		};
	}
}