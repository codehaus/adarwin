package org.ajester;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;

class MutatingClassAdapter extends ClassAdapter {
	private MutatingCodeAdapter adapter;
	private String className;

	public MutatingClassAdapter(final ClassVisitor classVisitor, MutatingCodeAdapter adapter) {
		super(classVisitor);
		this.adapter = adapter;
	}

	public CodeVisitor visitMethod(final int access, final String name, final String desc,
			final String[] exceptions) {
		
		//System.out.println("visitMethod:" + className + "." + name);
		adapter.setCodeVisitor(cv.visitMethod(access, name, desc, exceptions), name);
		return adapter;
	}
	/* (non-Javadoc)
	 * @see org.objectweb.asm.ClassVisitor#visit(int, java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void visit(int access, String name, String superName, String[] interfaces,
			String sourceFile) {
		
		this.className = name;
		super.visit(access, name, superName, interfaces, sourceFile);
	}
}

