package org.adarwin;

import org.adarwin.rule.Rule;

public interface RuleConsumer {
	RuleConsumer NULL = new RuleConsumer() {
		public boolean consume(Rule rule, RuleClassBindings ruleClassBindings) {
			return true;
		}
	};

	boolean consume(Rule rule, RuleClassBindings ruleClassBindings) throws ADarwinException;
}
