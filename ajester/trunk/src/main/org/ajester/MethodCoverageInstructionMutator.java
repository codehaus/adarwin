package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;

public class MethodCoverageInstructionMutator implements InstructionMatcher, InstructionMutator {
	private CodeMatcher codeMatcher;

	public MethodCoverageInstructionMutator(CodeMatcher codeMatcher) {
		this.codeMatcher = codeMatcher;
	}

	private static final String COVERAGE_CLASS = Coverage.class.getName().replace('.', '/');

	public boolean matches(Instruction instructionType) {
		if (instructionType instanceof MethodInstruction) {
			MethodInstruction methodInstruction =
			(MethodInstruction) instructionType;

			return !methodInstruction.getCodeLocation().getMethodName().equals("<init>") &&
			!methodInstruction.getCodeLocation().getMethodName().equals("<clinit>");
		}

		return false;
	}

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
	
	public CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
}