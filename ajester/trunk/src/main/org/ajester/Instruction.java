package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

public interface Instruction {
	public void visit(ClassVisitor classVisitor, CodeVisitor codeVisitor);
}