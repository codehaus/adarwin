package org.adarwin;

import org.adarwin.rule.ElementType;

public class ConstructorInvocation extends UsesCodeElement implements Constructor {
	private final String[] parameterTypes;

	public static CodeElement create(ClassName usesClassName, String[] parameterTypes) {
		return new ConstructorInvocation(usesClassName, parameterTypes);
	}

	private ConstructorInvocation(ClassName usesClassName, String[] parameterTypes) {
		super(usesClassName, ElementType.USES);
		this.parameterTypes = parameterTypes;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}
}
