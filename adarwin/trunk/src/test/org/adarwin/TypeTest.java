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

public class TypeTest extends TestCase {
	private TypeParser typeParser;
	protected void setUp() throws Exception {
		super.setUp();
		typeParser = new TypeParser();
	}

	public void testPrimatives() {
		assertEquals(Type.BOOLEAN, typeParser.parse("Z"));
		assertEquals(Type.BYTE, typeParser.parse("B"));
		assertEquals(Type.CHAR, typeParser.parse("C"));
		assertEquals(Type.DOUBLE, typeParser.parse("D"));
		assertEquals(Type.FLOAT, typeParser.parse("F"));
		assertEquals(Type.INT, typeParser.parse("I"));
		assertEquals(Type.LONG, typeParser.parse("J"));
		assertEquals(Type.SHORT, typeParser.parse("S"));
		assertEquals(Type.VOID, typeParser.parse("V"));
	}
	
	public void testPrimativeArrays() {
		assertArrayType(Type.BOOLEAN, "[Z");
		assertArrayType(Type.BYTE, "[B");
		assertArrayType(Type.CHAR, "[C");
		assertArrayType(Type.DOUBLE, "[D");
		assertArrayType(Type.FLOAT, "[F");
		assertArrayType(Type.INT, "[I");
		assertArrayType(Type.LONG, "[J");
		assertArrayType(Type.SHORT, "[S");
		assertArrayType(Type.VOID, "[V");
	}
	
	private void assertArrayType(IType expected, String form) {
		IType type = typeParser.parse(form);
		assertTrue(type.isPrimative());
		assertEquals(form, type.getForm());
		assertEquals(expected, type.getType());
	}

	public void testClasses() {
		assertEquals(String.class.getName(), typeParser.parse("Ljava/lang/String;").getTypeName());
		assertEquals(Integer.class.getName(), typeParser.parse("Ljava/lang/Integer;").getTypeName());
	}
	
	public void testClassArrays() {
		assertEquals(String.class.getName(), typeParser.parse("[Ljava/lang/String;").getTypeName());
	}

	public void testPrimativeMethodReturn() {
		assertEquals(Type.BOOLEAN, typeParser.parseMethodReturn("()Z"));
	}	

	public void testClassMethodReturn() {
		assertEquals(String.class.getName(),
			typeParser.parseMethodReturn("()Ljava/lang/String;").getTypeName());
	}
	
	public void testPrimativeMethodParameter() {
		Type[] expected = new Type[] {Type.BOOLEAN};
		
		assertTrue(Arrays.equals(expected, typeParser.parseMethodParameters("(Z)V")));
	}
	
	public void testClassMethodParameter() {
		IType[] expected = new IType[] {new Type(String.class.getName())};
		
		assertTrue(Arrays.equals(expected,
			typeParser.parseMethodParameters("(Ljava/lang/String;)V")));
	}
	
	public void testClassMethodParameters() {
		IType stringType = new Type(String.class.getName());
		IType[] expected = new IType[] {stringType, stringType};
		
		assertTrue(Arrays.equals(expected,
			typeParser.parseMethodParameters("(Ljava/lang/String;Ljava/lang/String;)V")));
	}

	private void printArray(String prefix, IType[] methodParams) {
		for (int i = 0; i < methodParams.length; i++) {
			System.out.println(prefix + "[" + i + "] = " + methodParams[i]);
		}
	}
}
