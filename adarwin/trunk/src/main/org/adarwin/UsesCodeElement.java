package org.adarwin;

import org.adarwin.rule.ElementType;

public class UsesCodeElement extends CodeElement {
	private final ClassName usesClassName;

	public static CodeElement create(ClassName usesClassName, ElementType codeType) {
		return new UsesCodeElement(usesClassName, codeType);
	}

	protected UsesCodeElement(ClassName usesClassName, ElementType codeType) {
		super(usesClassName, codeType);
		this.usesClassName = usesClassName;
	}

	public ClassName getUsesClassName() {
		return usesClassName;
	}
}
