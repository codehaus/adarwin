/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin.testmodel;

public class Simple {
	public static final String VOID_RETURN_METHOD = "voidReturnMethod";
	public static final String NO_ARG_METHOD = "noArgMethod";
	public static final String SINGLE_ARG_METHOD = "singleArgMethod";
	public static final String TWO_ARG_METHOD = "twoArgMethod";

	public void voidReturnMethod() {
	}

	public Integer noArgMethod() {
		return new Integer(0);
	}

	public Integer singleArgMethod(String parameter) {
		return new Integer(0);
	}
	
	public Integer twoArgMethod(String firstParameter, String secondParameter) {
		return new Integer(0);
	}
}
