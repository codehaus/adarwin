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


import java.util.Arrays;

public class ConstructorDeclaration extends CodeElement implements Constructor {
	private final String[] parameterTypes;

	public ConstructorDeclaration(String sourceClassName, String[] parameterTypes) {
		super(sourceClassName);
		this.parameterTypes = parameterTypes;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public int hashCode() {
		int hashCode = super.hashCode();

		for (int pLoop = 0; pLoop < parameterTypes.length; pLoop++) {
			hashCode |= parameterTypes[pLoop].hashCode();
		}

		return hashCode;
	}

	public boolean equals(Object obj) {
		if (obj == null ||
			!obj.getClass().equals(getClass())) {
			return false;
		}

		ConstructorDeclaration other = (ConstructorDeclaration) obj;		

		return Arrays.equals(parameterTypes, other.parameterTypes);
	}

	public boolean matches(String className, String[] parameterTypes) {
		return matches(className) && Util.matchesPatterns(getParameterTypes(), parameterTypes);
	}
}
