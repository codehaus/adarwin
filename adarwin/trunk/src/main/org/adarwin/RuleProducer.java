package org.adarwin;

public interface RuleProducer {
	void produce(RuleConsumer ruleConsumer);

	RuleIterator iterator();
}
