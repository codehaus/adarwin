package org.ajester;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.CodeVisitor;

public class ReuseableClassWriter implements ClassVisitor {
	private ClassWriter writer;

	public ReuseableClassWriter() {
		writer = createClassWriter();
	}

	public void visit(int access, String name, String superName, String[] interfaces,
		String sourceFile) {
		
		writer.visit(access, name, superName, interfaces, sourceFile);
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		writer.visitInnerClass(name, outerName, innerName, access);
	}

	public void visitField(int access, String name, String desc, Object value) {
		writer.visitField(access, name, desc, value);
	}

	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions) {
		return writer.visitMethod(access, name, desc, exceptions);
	}

	public void visitEnd() {
		writer.visitEnd();
	}
	
	public byte[] toByteArray() {
		return writer.toByteArray();
	}
	
	public void reset() {
		writer = createClassWriter();
	}

	private ClassWriter createClassWriter() {
		return new ClassWriter(true);
	}
}
