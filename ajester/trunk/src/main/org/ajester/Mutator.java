package org.ajester;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

import java.util.Set;

public interface Mutator extends ClassVisitor, CodeVisitor, InstructionMatcher, InstructionMutator {
	public byte[] visit(ClassReader reader);

	public Set getMutations();
}
