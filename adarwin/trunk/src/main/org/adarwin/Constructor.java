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


public class Constructor extends CodeElement {
	private final String[] parameterTypes;

	public static CodeElement createInvocation(String usesClassName, String[] parameterTypes) {
		return new Constructor(usesClassName, parameterTypes, CodeElement.USES);
	}

	public static CodeElement createDeclaration(String usesClassName, String[] parameterTypes) {
		return new Constructor(usesClassName, parameterTypes, CodeElement.SOURCE);
	}

	private Constructor(String usesClassName, String[] parameterTypes, int codeType) {
		super(usesClassName, codeType);
		this.parameterTypes = parameterTypes;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public boolean matches(String className, String[] parameterTypes) {
		return matches(className) && Util.matchesPatterns(getParameterTypes(), parameterTypes);
	}
}
