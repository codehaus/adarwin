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

import org.adarwin.rule.ElementType;

import java.util.Arrays;

public class ConstructorDeclaration extends CodeElement implements Constructor {
	private final String[] parameterTypes;
	private String toString;

	public ConstructorDeclaration(ClassName sourceClassName, String[] parameterTypes) {
		super(sourceClassName, ElementType.SOURCE);
		this.parameterTypes = parameterTypes;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public String toString() {
		synchronized (this) {
			if (toString == null) {
				StringBuffer buffer = new StringBuffer("Constructor(");
				Util.appendArray(buffer, parameterTypes);
				buffer.append(')');

				toString = buffer.toString();
			}
		}

		return toString;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null ||
			!obj.getClass().equals(getClass())) {
			return false;
		}

		ConstructorDeclaration other = (ConstructorDeclaration) obj;		

		return Arrays.equals(parameterTypes, other.parameterTypes);
	}
}
