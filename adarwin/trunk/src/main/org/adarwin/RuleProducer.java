package org.adarwin;

public interface RuleProducer {
	void produce(String expression, RuleConsumer ruleBuilderListener)
		throws ADarwinException;
}