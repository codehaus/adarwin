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

	private final ClassName className;
	private final ElementType elementType;

	public static CodeElement create(ClassName className, ElementType codeType) {
		if (ElementType.USES.equals(codeType)) {
			return UsesCodeElement.create(className);
		}

		return new CodeElement(className, codeType);
	}

	protected CodeElement(ClassName className, ElementType codeType) {
		this.className = className;
		this.elementType = codeType;
	}

	public ClassName getClassName() {
		return className;
	}

	public ElementType getType() {
		return elementType;
	}

	public boolean equals(Object object) {
		if (object == null || !object.getClass().equals(getClass())) {
			return false;
		}

		CodeElement other = (CodeElement) object;

		return getType().equals(other.getType()) &&
			getClassName().equals(other.getClassName());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		return Util.className(getClass()) + "(" + getClassName() + ", " + elementType + ")";
	}
}
