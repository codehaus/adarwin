package org.adarwin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;

import com.mockobjects.dynamic.OrderedMock;

public class RunnerTest extends TestCase {
	private static final String CLASSPATH = "target/classes";
	private static final String RULE = "package(org.adarwin.testmodel.a)";
	private static final String SECOND_RULE = "package(org.adarwin.testmodel.x)";
	private static final String COMPOSITE_RULE = RULE + ", " + SECOND_RULE;
	private OrderedMock mockLogger;
	private Runner runner;

	protected void setUp() throws Exception {
		super.setUp();
		mockLogger = new OrderedMock(Logger.class);
		runner = new Runner();
		runner.setLogger((Logger) mockLogger.proxy());
		runner.setBinding("rules.properties");
		runner.setClassPath(CLASSPATH);
		mockLogger.expect("log", "Evaluating rules against: " + CLASSPATH);
	}

	public void testRuleInAntFile() {
		mockLogger.expect("log", "3 classes violated: " + RULE);
		
		runner.setRuleExpression(RULE);
		
		try {
			runner.run();
			fail("RuleException expected");
		}
		catch (RuleException e) {
			assertEquals(Runner.RULE_VIOLATED, e.getMessage());
		}

		mockLogger.verify();
	}
	
	public void testRuleInFile() throws IOException {
		mockLogger.expect("log", "3 classes violated: " + RULE);
		
		runner.setRuleFileName(createTempRuleFile(RULE));
		
		executeTask();		

		mockLogger.verify();
	}

	public void testMultipleRulesOneRuleMatches() {
		mockLogger.expect("log", "3 classes violated: " + RULE);
				
		runner.setRuleExpression(COMPOSITE_RULE);
		
		executeTask();
		
		mockLogger.verify();
	}
	
	public void testMissingBinding() {
		runner.setBinding("");
		
		try {
			runner.run();
			fail("BuildException expected");
		}
		catch (RuleException be) {
			assertEquals(Runner.BINDING + Runner.MISSING_OR_EMPTY, be.getMessage());
		}
	}
	
	public void testMissingClassPath() {
		runner.setClassPath("");
		
		try {
			runner.run();
			fail("BuildException expected");
		}
		catch (RuleException be) {
			assertEquals(Runner.CLASSPATH + Runner.MISSING_OR_EMPTY, be.getMessage());
		}
	}

	public void testMissingRuleExpressionAndRuleFileName() {
		runner.setRuleExpression("");
		runner.setRuleFileName("");
		
		try {
			runner.run();
			fail("BuildException expected");
		}
		catch (RuleException be) {
			assertEquals("both ruleExpression and ruleFileName parameters are missing or empty", be.getMessage());
		}
	}

	private void executeTask() {
		try {
			runner.run();
			fail("BuildException expected");
		}		
		catch (RuleException be) {
			assertEquals(Runner.RULE_VIOLATED, be.getMessage());
		}
	}
	
	private String createTempRuleFile(String contents) throws IOException {
		File ruleFile = File.createTempFile("test-rule", "txt");
		OutputStream outputStream = new FileOutputStream(ruleFile);
		
		try {
			outputStream.write(contents.getBytes());
		}
		finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
			}	
		}
		
		return ruleFile.getAbsolutePath();
	}
	
}
