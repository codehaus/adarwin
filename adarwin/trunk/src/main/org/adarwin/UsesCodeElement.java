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

public class UsesCodeElement extends CodeElement {
	private final ClassName usesClassName;

	public static CodeElement create(ClassName usesClassName) {
		return new UsesCodeElement(usesClassName);
	}

	protected UsesCodeElement(ClassName usesClassName) {
		super(usesClassName, ElementType.USES);
		this.usesClassName = usesClassName;
	}

	public ClassName getUsesClassName() {
		return usesClassName;
	}
}
