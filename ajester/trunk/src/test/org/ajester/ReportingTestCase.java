package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;
import org.ajester.testmodel.IfStatement;
import org.ajester.testmodel.IfStatementTestCase;
import org.ajester.testmodel.ProblematicIfStatement;

import junit.framework.TestCase;

public class ReportingTestCase extends TestCase {
	public void testReport() throws Exception {
		AJester ajester = new AJester(BooleanReturn.class.getName(),
			BooleanReturnTestCase.class.getName(),
			new BooleanReturnCodeAdapter(BooleanReturn.class.getName()));
		Report report = ajester.run();
		assertEquals("BooleanReturn: No problems", report.getReport());
	}
	
	public void testReportWithAnotherClass() throws Exception {
		AJester ajester = new AJester(IfStatement.class.getName(),
			IfStatementTestCase.class.getName(),
			new BooleanReturnCodeAdapter(IfStatement.class.getName()));
		Report report = ajester.run();
		assertEquals("IfStatement: No problems", report.getReport());
	}
	
	public void testReportWithProblematicIfStatement() throws Exception {
		AJester ajester = new AJester(ProblematicIfStatement.class.getName(),
			BooleanReturnTestCase.class.getName(),
			new BooleanReturnCodeAdapter(ProblematicIfStatement.class.getName()));
		Report report = ajester.run();
		assertEquals("ProblematicIfStatement: Some problems", report.getReport());
	}
}
