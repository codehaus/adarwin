package org.ajester;

import org.ajester.testmodel.code.IfStatement;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import junit.framework.TestCase;

public class IfStatementMutatorTestCase extends TestCase {
	private IfStatementMutator mutator;
	private Mock mockCodeVisitor;

	protected void setUp() throws Exception {
		super.setUp();

		mockCodeVisitor = new Mock(CodeVisitor.class);
		
		mutator = new IfStatementMutator(IfStatement.IF_EQUAL_LOCATION);
		
		mutator.setCodeVisitor((CodeVisitor) mockCodeVisitor.proxy());
	}

	public void testMutatesMatchingMethod() {
		Label label = new Label();
		mockCodeVisitor.expect("visitJumpInsn", C.args(C.eq(Constants.IFNE), C.eq(label))); 

		mutator.setCurrentClassName(IfStatement.IF_EQUAL_LOCATION.getClassName());
		mutator.setMethodName(IfStatement.IF_EQUAL_LOCATION.getMethodName());
		mutator.visitJumpInsn(Constants.IFEQ, label);
		
		mockCodeVisitor.verify();
	}
}
