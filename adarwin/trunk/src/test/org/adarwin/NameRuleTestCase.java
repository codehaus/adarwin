package org.adarwin;

import org.adarwin.rule.AndRule;
import org.adarwin.rule.NameRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.TrueRule;

import junit.framework.TestCase;

public class NameRuleTestCase extends TestCase {
	private static final String SOME_NAME = "some name";
	
	private Grammar grammar;

	protected void setUp() throws Exception {
		super.setUp();

		grammar = new Grammar(
			new String[] {"name", "and", "true"},
			new Class[] {NameRule.class, AndRule.class, TrueRule.class}
		);
	}
	
	public void testExpression() {
		Rule rule = new NameRule(SOME_NAME, new AndRule(new Rule[] {new TrueRule(), new TrueRule()}));
		
		assertEquals(SOME_NAME, rule.getExpression(grammar));
	}
	
	public void testBuild() throws BuilderException {
		Rule rule = new RuleBuilder(grammar).buildRule("name(" + SOME_NAME + ", and(true, true))");
		
		assertEquals(SOME_NAME, rule.getExpression(grammar));
	}
}
