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


public class ConstructorInvocation extends UsesCodeElement implements Constructor {
	private final String[] parameterTypes;

	public static CodeElement create(ClassName usesClassName, String[] parameterTypes) {
		return new ConstructorInvocation(usesClassName, parameterTypes);
	}

	private ConstructorInvocation(ClassName usesClassName, String[] parameterTypes) {
		super(usesClassName);
		this.parameterTypes = parameterTypes;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}
}
