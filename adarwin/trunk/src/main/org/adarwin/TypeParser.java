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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TypeParser {
	public final Map primativeTypeMap;
	
	public TypeParser() {
		primativeTypeMap = new HashMap();
		primativeTypeMap.put("Z", Type.BOOLEAN);
		primativeTypeMap.put("B", Type.BYTE);
		primativeTypeMap.put("C", Type.CHAR);
		primativeTypeMap.put("D", Type.DOUBLE);
		primativeTypeMap.put("F", Type.FLOAT);
		primativeTypeMap.put("I", Type.INT);
		primativeTypeMap.put("J", Type.LONG);
		primativeTypeMap.put("S", Type.SHORT);
		primativeTypeMap.put("V", Type.VOID);
	}
	
	public IType parse(String toParse) {
		IType type = null;
		if (toParse.startsWith("[")) {
			type = new ArrayType(parse(toParse.substring(1)));
		}
		else if ('L' == toParse.charAt(0)) {
			toParse = toParse.substring(1, toParse.indexOf(';'));
			type = new Type(toParse.replace('/', '.'));
		}		
		else {
			type = (IType) primativeTypeMap.get(toParse.substring(0, 1));
		}
		
		return type;
	}
	
	public IType parseMethodReturn(String toParse) {
		return parse(toParse.substring(toParse.indexOf(')') + 1));
	}

	public IType[] parseMethodParameters(String toParse) {
		String parameters = toParse.substring(1, toParse.indexOf(')'));
		
		return parseMulti(parameters);
	}

	private IType[] parseMulti(String parameters) {
		List types = new LinkedList();
		
		String toParse = parameters;
		
		while (toParse.length() > 0) {
			IType type = parse(toParse);
			types.add(type);
			toParse = toParse.substring(type.getForm().length());
		}
		
		return (IType[]) types.toArray(new IType[0]);
	}
}
