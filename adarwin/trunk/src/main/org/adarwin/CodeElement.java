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

public class CodeElement {
	public static final CodeElement[] EMPTY_ARRAY = new CodeElement[0];

	private final String packageName;
	private final String className;
	private final String fullyQualifiedClassName;
	private ElementType elementType;

	public CodeElement(String fullyQualifiedClassName, ElementType codeType) {
		this.packageName = packageName(fullyQualifiedClassName);
		this.className = className(fullyQualifiedClassName);
		this.fullyQualifiedClassName = fullyQualifiedClassName;
		this.elementType = codeType;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public ElementType getType() {
		return elementType;
	}

	public String getFullyQualifiedClassName() {
		return fullyQualifiedClassName;
	}

	public boolean equals(Object object) {
		if (object == null || !object.getClass().equals(getClass())) {
			return false;
		}

		CodeElement other = (CodeElement) object;

		return getType().equals(other.getType()) &&
			getFullyQualifiedClassName().equals(other.getFullyQualifiedClassName());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		return "CodeElement(" + packageName + ", " + className + ", " + elementType + ")";
	}

	private static String packageName(String fullyQualifiedClassName) {
		if (fullyQualifiedClassName.indexOf('.') != -1) {
			return fullyQualifiedClassName.substring(0, fullyQualifiedClassName.lastIndexOf('.'));
		}
		else {
			return "";
		}
	}

	private static String className(String fullyQualifiedClassName) {
		if (fullyQualifiedClassName.indexOf('.') != -1) {
			return fullyQualifiedClassName.substring(fullyQualifiedClassName.lastIndexOf('.') + 1);
		}
		else {
			return fullyQualifiedClassName;
		}
	}
}
