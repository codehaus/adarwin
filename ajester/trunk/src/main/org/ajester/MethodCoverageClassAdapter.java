package org.ajester;

import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;

public class MethodCoverageClassAdapter extends MutatingClassAdapter {
	private static final String COVERAGE_CLASS = Coverage.class.getName().replace('.', '/');
	
	private String methodCovered;

	public MethodCoverageClassAdapter(String classToMutate) {
		super(classToMutate);
	}
	
	public String getMethodCovered() {
		return methodCovered;
	}
	
	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions) {
		CodeVisitor codeVisitor = super.visitMethod(access, name, desc, exceptions);
		
		if (!name.equals("<init>")) {
			codeVisitor.visitLdcInsn(getCurrentClassName());
			codeVisitor.visitLdcInsn(name);
			codeVisitor.visitMethodInsn(Constants.INVOKESTATIC, COVERAGE_CLASS,
				Coverage.METHOD_COVERED, "(Ljava/lang/String;Ljava/lang/String;)V");
		}
		else {
			// walk over invocation of this(...) or super(...), then do as above
		}
		
		return codeVisitor;
	}	
}
