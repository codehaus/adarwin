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

import java.util.Set;

public class ClassSummary {
	private final String className;
	private final Set constructors;
	private final Set methods;
	private final Set dependancies;

    public ClassSummary(String className, Set constructors, Set methods, Set dependancies) {
		this.className = className;
    	this.constructors = constructors;
		this.methods = methods;
		this.dependancies = dependancies;
    }

	public String getClassName() {
		return className;
	}

	public Set getConstructors() {
		return constructors;
	}

	public Set getMethods() {
		return methods;
	}

	public Set getDependancies() {
		return dependancies;
	}

	public ClassSummary updateDependancies(Set dependancies) {
		if (dependancies == null) {
			dependancies = getDependancies();
		}
		
		return new ClassSummary(getClassName(), getConstructors(), getMethods(), dependancies);
	}
}
