package org.ajester;

import org.ajester.testmodel.IfStatement;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import junit.framework.TestCase;

public class IfStatementMutatorTestCase extends TestCase {
	private static final String MATCHING_METHOD = "matchingMethod";
	private static final String NON_MATCHING_METHOD = "nonMatchingMethod";
	private IfStatementMutator mutator;
	private Mock mockCodeVisitor;

	protected void setUp() throws Exception {
		super.setUp();

		mockCodeVisitor = new Mock(CodeVisitor.class);
		
		mutator = new IfStatementMutator(IfStatement.IF_EQUAL_LOCATION);
		
		mutator.setCodeVisitor((CodeVisitor) mockCodeVisitor.proxy());
	}
	
	public void testMatchingMethod() {
		Label label = new Label();
		mockCodeVisitor.expect("visitJumpInsn", C.args(C.eq(Constants.IFNE), C.eq(label))); 

		mutator.setMethodName(MATCHING_METHOD);
		mutator.visitJumpInsn(Constants.IFEQ, label);
		
		mockCodeVisitor.verify();
	}
}
