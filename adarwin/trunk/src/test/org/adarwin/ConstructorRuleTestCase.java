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

import org.adarwin.rule.ConstructorRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.UsesRule;

import java.util.Date;

public class ConstructorRuleTestCase extends RuleTestCase {
	static class HasZeroArgConstructor {
		public HasZeroArgConstructor() {
		}
	}

	public void testZeroArgMatchingConstructor() throws ADarwinException {
		assertNumMatches(1, createConstructorRule(new Class[0]), HasZeroArgConstructor.class);
	}

	static class DoesNotHaveZeroArgConstructor {
		public DoesNotHaveZeroArgConstructor(Integer integer) {
		}
	}

	public void testZeroArgNonMatchingConstructor() throws ADarwinException {
		assertNumMatches(0, createConstructorRule(new Class[0]),
			DoesNotHaveZeroArgConstructor.class);
	}

	static class HasSingleArgConstructor {
		public HasSingleArgConstructor(Integer integer) {
		}
	}

	public void testSingleArgMatchingConstructor() throws ADarwinException {
		assertNumMatches(1, createConstructorRule(Integer.class), HasSingleArgConstructor.class);
	}

	static class DoesNotHaveSingleArgConstructor {
		public DoesNotHaveSingleArgConstructor(Integer integer, String string) {
		}
	}

	public void testSingleArgNonMatchingConstructor() throws ADarwinException {
		assertNumMatches(0, createConstructorRule(Integer.class),
			DoesNotHaveSingleArgConstructor.class);
	}

	static class HasTwoArgConstructor {
		public HasTwoArgConstructor(Integer integer, String string) {
		}
	}

	public void testTwoArgMatchingConstructor() throws ADarwinException {
		assertNumMatches(1, createConstructorRule(new Class[] {Integer.class, String.class}),
			HasTwoArgConstructor.class);		
	}

	static class DoesNotHaveTwoArgConstructor {
		public DoesNotHaveTwoArgConstructor(Integer integer) {
		}
	}

	public void testTwoArgNonMatchingConstructor() throws ADarwinException {
		assertNumMatches(0, createConstructorRule(new Class[] {Integer.class, String.class}),
			DoesNotHaveTwoArgConstructor.class);		
	}

	static class InvokesZeroArgConstructor {
		public void method() {
			new Date();
		}
	}

	public void testInvokingConstructor() throws ADarwinException {
		Rule rule = new UsesRule(ConstructorRule.create(Date.class.getName(), (new String[0])));

		assertNumMatches(1, rule, InvokesZeroArgConstructor.class);
	}

	class InvokesButDoesNotHaveAConstructorWithAStringParameter {
		public void method() {
			new StringBuffer("");
		}
	}

	public void testInvokingConstructorNotRegardedAsHavingConstructor() throws ADarwinException {
		assertNumMatches(0, new SourceRule(createConstructorRule(String.class)),
			InvokesButDoesNotHaveAConstructorWithAStringParameter.class);
	}

	static class HasButDoesNotInvokeConstructorWithStringParameter {
		public HasButDoesNotInvokeConstructorWithStringParameter(String param) {
		}
	}

	public void testHavingConstructorNotRegardedAsInvokingConstructor() throws ADarwinException {
		Rule rule = new UsesRule(ConstructorRule.create(
			Class.class.getName(), (new String[] {String.class.getName()})));

		assertNumMatches(0, rule, HasButDoesNotInvokeConstructorWithStringParameter.class);
	}

	private Rule createConstructorRule(Class parameterType) {
		return createConstructorRule(new Class[] {parameterType});
	}
	
	private Rule createConstructorRule(Class[] parameterTypes) {
		return ConstructorRule.create(".*", Util.convertClassArrayToStringArray(parameterTypes));
	}
}
