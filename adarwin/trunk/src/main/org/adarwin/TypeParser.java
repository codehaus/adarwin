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

import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

public class TypeParser {
	private final Map objectWebTypeMap = new HashMap();

	public TypeParser() {
		addObjectWebType(boolean.class, Type.BOOLEAN_TYPE);
		addObjectWebType(byte.class, Type.BYTE_TYPE);
		addObjectWebType(char.class, Type.CHAR_TYPE);
		addObjectWebType(double.class, Type.DOUBLE_TYPE);
		addObjectWebType(float.class, Type.FLOAT_TYPE);
		addObjectWebType(int.class, Type.INT_TYPE);
		addObjectWebType(long.class, Type.LONG_TYPE);
		addObjectWebType(short.class, Type.SHORT_TYPE);
		addObjectWebType(void.class, Type.VOID_TYPE);
	}

	public boolean isPrimative(String desc) {
		return isPrimative(Type.getType(desc));
	}

	public boolean isMethodReturnPrimative(String desc) {
		return isPrimative(Type.getReturnType(desc));
	}

	public String typeName(String desc) {
		return typeName(descendArray(Type.getType(desc)));
	}

	public String returnType(String desc) {
		return typeName(Type.getReturnType(desc));
	}
	
	public String[] parameterTypes(String desc) {
		Type[] types = Type.getArgumentTypes(desc);
		
		String[] methodParameters = new String[types.length];
		
		for (int tLoop = 0; tLoop < types.length; tLoop++) {
			methodParameters[tLoop] = typeName(types[tLoop]);
		}
		
		return methodParameters;
	}

	private boolean isPrimative(Type type) {
		return primativeTypeName(type) != null;
	}

	private String typeName(Type type) {
		if (isPrimative(type)) {
			return primativeTypeName(type);
		}
		else {
			return descendArray(type).getClassName();
		}
	}

	private String primativeTypeName(Type type) {
		return (String) objectWebTypeMap.get(descendArray(type));
	}

	private Type descendArray(Type type) {
		while (type.getSort() == Type.ARRAY) {
			type = type.getElementType();
		}
		return type;
	}

	private void addObjectWebType(Class typeClass, Type type) {
		objectWebTypeMap.put(type, typeClass.getName());
	}
}
