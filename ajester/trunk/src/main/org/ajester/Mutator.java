package org.ajester;

import org.objectweb.asm.ClassReader;

public interface Mutator {
	public byte[] visit(ClassReader reader);
}
