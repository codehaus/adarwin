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
	private Set dependancies;
    private String className;

    public ClassSummary(Set dependancies) {
        this.dependancies = dependancies;
    }

    public ClassSummary(Set dependancies, String className) {
		this.dependancies = dependancies;
        this.className = className;
    }

	public boolean equals(Object object) {
		if (object == null || !object.getClass().equals(getClass())) {
			return false;
		}

		ClassSummary other = (ClassSummary) object;

		return getDependancies().equals(other.getDependancies());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		return "ClassSummary(" + getDependancies() + ")";
	}

	public Set getDependancies() {
		return dependancies;
	}

    public String getClassName() {
        return className;
    }
}
