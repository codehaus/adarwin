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

import org.adarwin.rule.MethodRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.UsesRule;

public class MethodRuleTestCase extends RuleTestCase {
	public void testVoidReturnMethod() {
		class ClassWithVoidReturnMethod {
			public void voidReturnMethod() {
			}
		}

		assertNumMatches(1, createMethodRule("voidReturnMethod", Void.TYPE, new Class[0]),
			ClassWithVoidReturnMethod.class);
	}

	public void testNoArgMethod() {
		class ClassWithNoArgMethod {
			public Integer noArgMethod() {
				return null;
			}
		}

		assertNumMatches(1, createMethodRule("noArgMethod", Integer.class),
			ClassWithNoArgMethod.class);
	}

	public void testSingleArgMethod() {
		class ClassWithSingleArgMethod {
			public Integer singleArgMethod(String string) {
				return null;
			}
		}

		assertNumMatches(1, createMethodRule("singleArgMethod", Integer.class,
			new Class[] {String.class}), ClassWithSingleArgMethod.class);
	}

	public void testTwoArgMethod() {
		class ClassWithTwoArgMethod {
			public Integer twoArgMethod(Integer integer, String string) {
				return null;
			}
		}

		assertNumMatches(1, createMethodRule("twoArgMethod", Integer.class,
			new Class[] {Integer.class, String.class}), ClassWithTwoArgMethod.class);
	}

	public void testMethodReturningPrimitive() {
		class ClassWithMethodReturningPrimitive {
			public int methodReturningPrimitive() {
				return 0;
			}
		}

		assertNumMatches(1, createMethodRule("methodReturningPrimitive", Integer.TYPE),
			ClassWithMethodReturningPrimitive.class);
	}

	public void testMethodDeclaration() {
		class ClassWithNoArgMethodReturningInteger {
			public Integer noArgMethod() {
				return null;
			}
		}

		Rule rule = new SourceRule(createMethodRule("noArgMethod", Integer.class));

		assertNumMatches(1, rule, ClassWithNoArgMethodReturningInteger.class);
	}

	public void testSelf() {
		Rule rule = MethodRule.create(Void.TYPE.getName(), "testSelf", new String[0]); 

		assertNumMatches(1, rule, MethodRuleTestCase.class);
	}

	public void testMethodInvocation() {
		class InvokesMethod {
			public void method() {
				new Integer(0).toString();
			}
		}

		Rule rule = new UsesRule(createMethodRule("toString", String.class, new Class[0]));

		assertNumMatches(1, rule, InvokesMethod.class);
	}

	public void testUsingAMethodNotRegardedAsHavingSaidMethod() {
		class InvokesMethod {
			public void method() {
				new Integer(0).toString();
			}
		}

		assertNumMatches(0, new SourceRule(createMethodRule("toString", String.class)),
			InvokesMethod.class);
	}

	private Rule createMethodRule(String methodName, Class returnType) {
		return MethodRule.create(returnType.getName(), methodName, new String[0]);
	}

	private Rule createMethodRule(String methodName, Class returnType, Class[] parameterTypes) {
		return MethodRule.create(returnType.getName(), methodName,
			Util.convertClassArrayToStringArray(parameterTypes));
	}
}
