package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;
import org.ajester.testmodel.IfStatement;
import org.ajester.testmodel.IfStatementTestCase;
import org.ajester.testmodel.ProblematicIfStatement;
import org.ajester.testmodel.ProblematicIfStatementTestCase;

import junit.framework.TestCase;

public class ReportingTestCase extends TestCase {
	public void testReport() throws Exception {
		AJester ajester = new AJester(BooleanReturn.class, BooleanReturnTestCase.class,
			new BooleanReturnMutator(BooleanReturn.GET_TRUE_LOCATION));
		assertEquals("No problems: BooleanReturn", ajester.run().getReport());
	}
	
	public void testReportWithAnotherClass() throws Exception {
		AJester ajester = new AJester(IfStatement.class, IfStatementTestCase.class,
			new BooleanReturnMutator(IfStatement.IF_EQUAL_LOCATION));
		assertEquals("No problems: IfStatement", ajester.run().getReport());
	}
	
	public void testReportWithProblematicIfStatement() throws Exception {
		AJester ajester = new AJester(ProblematicIfStatement.class,
			ProblematicIfStatementTestCase.class,
			new IfStatementMutator(ProblematicIfStatement.IF_EQUAL_LOCATION));
		assertEquals("Some problems: ProblematicIfStatement", ajester.run().getReport());
	}
	
//	public void testReportWithProblematicIfStatementWhenModifyingDifferentMethod() throws Exception {
//		AJester ajester = new AJester(ProblematicIfStatement.class.getName(),
//			ProblematicIfStatementTestCase.class.getName(),
//			new IfStatementCodeAdapter(ProblematicIfStatement.class.getName(), "nonExistantMethod"));
//		Report report = ajester.run();
//		assertEquals("No problems: ProblematicIfStatement", report.getReport());
//	}
	
	// What is the difference between these two tests, what makes me expect some problems for
	// one and no problems for the other. One involved mutating methods that are used (exist),
	// the other involves mutating methods that are not used (do not exist).
	// When a method is mutated that is not used I do not expect the tests to care, hence I do
	// not expect there to be a problem, however I have no means of knowing (yet) if a method
	// is used (covered).
	
	// A more refined notion of 'some problems' is this: methodX was modified and tests covered
	// methodX and still passed.
	
	// Let me see spike method coverage before tackling this refinement
}
