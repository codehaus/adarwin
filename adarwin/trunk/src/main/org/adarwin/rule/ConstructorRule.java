/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin.rule;

import org.adarwin.ClassSummary;
import org.adarwin.Constructor;
import org.adarwin.RuleClassBindings;

public class ConstructorRule implements Rule {
	private String[] parameterTypes;
	
	public ConstructorRule() {
		this(new String[0]);
	}

	public ConstructorRule(String[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
	public ConstructorRule(Class[] parameterTypes) {
		this(convertClassArrayToStringArray(parameterTypes));
	}

	public boolean inspect(ClassSummary classSummary) {
		return classSummary.getConstructors().contains(new Constructor(parameterTypes));
	}

	public String toString(RuleClassBindings ruleClassBindings) {
		StringBuffer buffer = new StringBuffer(ruleClassBindings.getRule(getClass()));
		buffer.append('(');
		
		for (int pLoop = 0; pLoop < parameterTypes.length; ++pLoop) {
			if (pLoop != 0) {
				buffer.append(", ");
			}
			buffer.append(parameterTypes[pLoop]);
		}
		
		buffer.append(')');
		return buffer.toString();
	}

	private static String[] convertClassArrayToStringArray(Class[] classParameterTypes) {
		String[] parameterTypes = new String[classParameterTypes.length];
		
		for (int cLoop = 0; cLoop < classParameterTypes.length; ++cLoop) {
			parameterTypes[cLoop] = classParameterTypes[cLoop].getName();
		}
		
		return parameterTypes;
	}
}
