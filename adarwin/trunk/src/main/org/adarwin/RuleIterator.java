package org.adarwin;

import org.adarwin.rule.Rule;

public interface RuleIterator {
	boolean hasNext();

	Rule next();
}
