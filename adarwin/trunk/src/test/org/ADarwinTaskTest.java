package org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.adarwin.ant.ADarwinTask;
import org.adarwin.ant.ADarwinTask.ILogger;
import org.apache.tools.ant.BuildException;

import com.mockobjects.dynamic.OrderedMock;

import junit.framework.TestCase;

public class ADarwinTaskTest extends TestCase {
	private static final String RULE = "package(org.adarwin.testmodel.a)";
	private static final String SECOND_RULE = "package(org.adarwin.testmodel.x)";
	private static final String COMPOSITE_RULE = RULE + ", " + SECOND_RULE;
	private static final String TARGET_SUMMARY =
		"aDarwin checking classpath: target/classes, against the rule: package(org.adarwin.testmodel.a)";
	private OrderedMock mockLogger;
	private ADarwinTask task;

	protected void setUp() throws Exception {
		super.setUp();
		mockLogger = new OrderedMock(ADarwinTask.ILogger.class);
		task = new ADarwinTask();
		task.setLogger((ILogger) mockLogger.proxy());
		task.setBinding("rules.properties");
		task.setClassPath("target/classes");
	}

	public void testRuleInAntFile() {
		mockLogger.expect("log", TARGET_SUMMARY);
		mockLogger.expect("log", "3 classes matched: " + RULE);
				
		task.setRuleExpression(RULE);
		
		executeTask();

		mockLogger.verify();
	}
	
	public void testRuleInFile() throws IOException {
		mockLogger.expect("log", TARGET_SUMMARY);
		mockLogger.expect("log", "3 classes matched: " + RULE);
		
		task.setRuleFileName(createTempRuleFile(RULE));
		
		executeTask();		

		mockLogger.verify();
	}

	public void testMultipleRulesOneRuleMatches() {
		mockLogger.expect("log", TARGET_SUMMARY);
		mockLogger.expect("log", "3 classes matched: " + RULE);
				
		task.setRuleExpression(COMPOSITE_RULE);
		
		executeTask();
		
		mockLogger.verify();
	}

	private void executeTask() {
		try {
			task.execute();
			fail("BuildException expected");
		}		
		catch (BuildException be) {
			assertEquals("aDarwin Rule Violated", be.getMessage());
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
