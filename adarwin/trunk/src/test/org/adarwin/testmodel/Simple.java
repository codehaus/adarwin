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

import org.adarwin.ClassName;
import org.adarwin.Method;
import org.adarwin.MethodDeclaration;
import org.adarwin.Util;

public class Simple {
	public static final MethodDeclaration VOID_RETURN_METHOD =
		create("voidReturnMethod", Void.TYPE, new Class[0]);

	public static final MethodDeclaration NO_ARG_METHOD =
		create("noArgMethod", Integer.class, new Class[0]);

	public static final MethodDeclaration SINGLE_ARG_METHOD =
		create("singleArgMethod", Integer.class, new Class[] {String.class});

	public static final MethodDeclaration TWO_ARG_METHOD =
		create("twoArgMethod", Integer.class, new Class[] {String.class, String.class});

	public static final MethodDeclaration METHOD_RETURNING_PRIMITIVE = 
		create("methodReturningPrimitive", Integer.TYPE, new Class[0]);

	public Simple() {
	}
	
	public Simple(String parameter) {
	}
	
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
	
	public int methodReturningPrimitive() {
		return 0;
	}

	private static MethodDeclaration create(String methodName, Class returnType, Class[] parameterTypes) {
		return new MethodDeclaration(new ClassName(Simple.class.getName()), methodName, returnType.getName(),
			Util.convertClassArrayToStringArray(parameterTypes));
	}
}
