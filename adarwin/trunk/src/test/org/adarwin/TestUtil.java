/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin;

import java.io.IOException;

import org.adarwin.rule.Rule;

import junit.framework.Assert;

public class TestUtil {
	public void assertRuleMatchingCount(int expectedCount, Rule rule, Class clazz)
		throws IOException {
		
		Assert.assertEquals(expectedCount, new ClassFile(clazz).evaluate(rule).getCount());
	}
}
