package org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.adarwin.ant.ADarwinTask;
import org.adarwin.ant.ADarwinTask.ILogger;
import org.apache.tools.ant.BuildException;

import com.mockobjects.dynamic.OrderedMock;

public class ADarwinTaskTest extends TestCase {
	private static final String CLASSPATH = "target/classes";
	private static final String RULE = "package(org.adarwin.testmodel.a)";
	private static final String SECOND_RULE = "package(org.adarwin.testmodel.x)";
	private static final String COMPOSITE_RULE = RULE + ", " + SECOND_RULE;
	private OrderedMock mockLogger;
	private ADarwinTask task;

	protected void setUp() throws Exception {
		super.setUp();
		mockLogger = new OrderedMock(ADarwinTask.ILogger.class);
		task = new ADarwinTask();
		task.setLogger((ILogger) mockLogger.proxy());
		task.setBinding("rules.properties");
		task.setClassPath(CLASSPATH);
		mockLogger.expect("log", "Evaluating rules against: " + CLASSPATH);
	}

	public void testRuleInAntFile() {
		mockLogger.expect("log", "3 classes violated: " + RULE);

		task.setRuleExpression(RULE);
		
		executeTask();

		mockLogger.verify();
	}
	
	public void testRuleInFile() throws IOException {
		mockLogger.expect("log", "3 classes violated: " + RULE);
		
		task.setRuleFileName(createTempRuleFile(RULE));
		
		executeTask();		

		mockLogger.verify();
	}

	public void testMultipleRulesOneRuleMatches() {
		mockLogger.expect("log", "3 classes violated: " + RULE);
				
		task.setRuleExpression(COMPOSITE_RULE);
		
		executeTask();
		
		mockLogger.verify();
	}
	
	public void testMissingBinding() {
		task.setBinding("");
		
		try {
			task.execute();
			fail("BuildException expected");
		}
		catch (BuildException be) {
			assertEquals(ADarwinTask.BINDING + ADarwinTask.MISSING_OR_EMPTY, be.getMessage());
		}
	}
	
	public void testMissingClassPath() {
		task.setClassPath("");
		
		try {
			task.execute();
			fail("BuildException expected");
		}
		catch (BuildException be) {
			assertEquals(ADarwinTask.CLASSPATH + ADarwinTask.MISSING_OR_EMPTY, be.getMessage());
		}
	}

	public void testMissingRuleExpressionAndRuleFileName() {
		task.setRuleExpression("");
		task.setRuleFileName("");
		
		try {
			task.execute();
			fail("BuildException expected");
		}
		catch (BuildException be) {
			assertEquals("both ruleExpression and ruleFileName parameters are missing or empty", be.getMessage());
		}
	}

	private void executeTask() {
		try {
			task.execute();
			fail("BuildException expected");
		}		
		catch (BuildException be) {
			assertEquals(ADarwinTask.RULE_VIOLATED, be.getMessage());
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
