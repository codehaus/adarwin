package org.adarwin.rule;

import org.adarwin.ClassSummary;
import org.adarwin.Grammar;

public class NameRule implements Rule {
	private Rule rule;
	private String name;

	public NameRule(String name, Rule rule) {
		this.name = name;
		this.rule = rule;
	}
	
	public boolean inspect(ClassSummary classSummary) {
		return rule.inspect(classSummary);
	}

	public String getExpression(Grammar grammar) {
		return name;
	}
	
}
