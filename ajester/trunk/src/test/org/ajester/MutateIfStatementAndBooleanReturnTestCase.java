package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnAndIfStatementTestSuite;
import org.ajester.testmodel.IfStatement;

import junit.framework.TestCase;

public class MutateIfStatementAndBooleanReturnTestCase extends TestCase {
	public void atestMutatingTwoClasses() throws Exception {
		Mutator booleanReturnMutator = new BooleanReturnMutator(BooleanReturn.GET_TRUE_LOCATION);
		Mutator ifStatementMutator = new IfStatementMutator(IfStatement.IF_EQUAL_LOCATION);
		
		TestResults results = new TestRunnerWrapper().run(
			BooleanReturnAndIfStatementTestSuite.class.getName(),
			new AggregateMutator(booleanReturnMutator, ifStatementMutator));

		assertEquals(2, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}
}
