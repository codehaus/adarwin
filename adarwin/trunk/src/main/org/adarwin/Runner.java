package org.adarwin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.adarwin.rule.Rule;

public class Runner {
	public static final String BINDING = "binding";
	public static final String CLASSPATH = "classPath";
	public static final String RULE_VIOLATED = "aDarwin rule violated";
	public static final String MISSING_OR_EMPTY = " parameter missing or empty";

	private Logger logger;
	private String binding;
	private String classPath;
	private String ruleExpression;
	private String ruleFileName;
	private boolean print;
	private boolean failOnMatch = true;

	public void setFailOnMatch(boolean failOnMatch) {
		this.failOnMatch = failOnMatch;
	}

	public boolean isFailOnMatch() {
		return failOnMatch;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public boolean getPrint() {
		return print;
	}

	public void setRuleExpression(String rule) {
		this.ruleExpression = rule;
	}

	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}

	public String getBinding() {
		return binding;
	}

	public String getClassPath() {
		return classPath;
	}

	public Logger getLogger() {
		return logger;
	}

	public String getRuleExpression() {
		return ruleExpression;
	}

	public String getRuleFileName() {
		return ruleFileName;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public void run() throws RuleException {
		verifyParameter(getBinding(), Runner.BINDING);
		if (isParameterNullOrEmpty(getClassPath())) {
			throw new RuleException(Runner.CLASSPATH + Runner.MISSING_OR_EMPTY);
		}

		if (isParameterNullOrEmpty(getRuleExpression()) &&
			isParameterNullOrEmpty(getRuleFileName())) {
			throw new RuleException("both ruleExpression and ruleFileName parameters are missing or empty");		
		}
		
		try {
			RuleClassBindings ruleClassBindings = new RuleClassBindings(getBinding());
			Rule[] rules = new RuleBuilder(ruleClassBindings).buildRules(acquireRuleExpression());
            
			logger.log("Evaluating rules against: " + getClassPath());

			boolean throwException = false;
            
			for (int rLoop = 0; rLoop < rules.length; rLoop++) {
				throwException = throwException | executeRule(ruleClassBindings, rules[rLoop]);	
			}
			
			if (throwException) {
				throw new RuleException(Runner.RULE_VIOLATED);
			}
		} catch (IOException e) {
			throw new RuleException("Unable to find classpath(?): " + getClassPath(), e);
		} catch (BuilderException e) {
			e.printStackTrace();
			throw new RuleException(e);
		} catch (ClassNotFoundException e) {
			throw new RuleException("Unable to find classpath(?): " + getClassPath(), e);
		}
	}

	private void verifyParameter(String value, String name) throws RuleException {
		if (isParameterNullOrEmpty(value)) {
			throw new RuleException(name + Runner.MISSING_OR_EMPTY);
		}
	}

	private boolean executeRule(RuleClassBindings ruleClassBindings, Rule rule) throws IOException, FileNotFoundException {
		String ruleExpression = rule.toString(ruleClassBindings);
		
		Result result = createCodeFactory().create(getClassPath()).evaluate(rule);
		if (result.getCount() > 0) {
			logger.log(result.getCount() + " classes violated: " + ruleExpression);
			if (getPrint()) {
				for (Iterator iterator = result.getMatchingClasses().iterator(); iterator.hasNext();) {
					logger.log("  " + (String) iterator.next());
				}
			}
		}

		return isFailOnMatch() && result.getCount() > 0;
	}

	private ICodeFactory createCodeFactory() {
		return new LazyCodeFactory(new CodeFactory());
	}

	private boolean isParameterNullOrEmpty(String value) {
		return value == null || value.length() == 0;
	}

	private String acquireRuleExpression() throws IOException {
		if (getRuleExpression() != null) {
			return getRuleExpression();
		}
		else {
			return readRuleExpressionFromFile();    		
		}
	}

	private String readRuleExpressionFromFile() throws FileNotFoundException, IOException {
		File ruleFile = new File(getRuleFileName());
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ruleFile)));

		try {
			StringBuffer buffer = new StringBuffer();
			
			String currentLine = reader.readLine();
			
			while (currentLine != null) {
				buffer.append(currentLine);
				currentLine = reader.readLine();
			}
			
			return buffer.toString();
		}
		finally {
			reader.close();
		}
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
