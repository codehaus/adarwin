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

import java.util.Arrays;

import junit.framework.TestCase;

public class TypeTestCase extends TestCase {
	private final TypeParser typeParser = new TypeParser();

	public void testPrimatives() {
		assertType(boolean.class, "Z");
		assertType(byte.class, "B");
		assertType(char.class, "C");
		assertType(double.class, "D");
		assertType(float.class, "F");
		assertType(int.class, "I");
		assertType(long.class, "J");
		assertType(short.class, "S");
		assertType(void.class, "V");
	}

	public void testPrimativeArrays() {
		assertType(boolean.class, "[Z");
		assertType(byte.class, "[B");
		assertType(char.class, "[C");
		assertType(double.class, "[D");
		assertType(float.class, "[F");
		assertType(int.class, "[I");
		assertType(long.class, "[J");
		assertType(short.class, "[S");
		assertType(void.class, "[V");
	}

	public void testClasses() {
		assertEquals(String.class.getName(), typeName("Ljava/lang/String;"));
		assertEquals(Integer.class.getName(), typeName("Ljava/lang/Integer;"));
	}

	public void testClassArrays() {
		assertEquals(String.class.getName(), typeName("[Ljava/lang/String;"));
	}

	public void testPrimativeMethodReturn() {
		assertEquals("boolean", returnType("()Z")); 
	}	

	public void testClassMethodReturn() {
		assertEquals(String.class.getName(), returnType("()Ljava/lang/String;"));
	}

	public void testPrimativeMethodParameter() {
		assertTrue(Arrays.equals(new String[] {"boolean"}, parameterTypes("(Z)V")));
	}

	public void testClassMethodParameter() {
		assertTrue(Arrays.equals(new String[] {String.class.getName()},
			parameterTypes("(Ljava/lang/String;)V")));
	}

	public void testClassMethodParameters() {
		String[] expected = new String[] {String.class.getName(), String.class.getName()};

		assertTrue(Arrays.equals(expected,
			parameterTypes("(Ljava/lang/String;Ljava/lang/String;)V")));
	}

	private void assertType(Class expectedClass, String form) {
		assertType(expectedClass.getName(), form);
	}

	private void assertType(String expected, String form) {
		assertTrue(typeParser.isPrimative(form));
		assertEquals(expected, typeName(form));
	}

	private String typeName(String form) {
		return typeParser.typeName(form);
	}

	private String returnType(String form) {
		return typeParser.returnType(form);
	}

	private String[] parameterTypes(String form) {
		return typeParser.parameterTypes(form);
	}
}
