package org.ajester;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import junit.runner.TestCaseClassLoader;
import junit.runner.TestSuiteLoader;

public class MutatingClassLoader extends TestCaseClassLoader implements TestSuiteLoader {
	private MutatingCodeAdapter mutatingCodeAdapter;

	public MutatingClassLoader(MutatingCodeAdapter mutatingCodeAdapter) {
		this.mutatingCodeAdapter = mutatingCodeAdapter;
	}
	
	public Class load(String suiteClassName) throws ClassNotFoundException {
		return loadClass(suiteClassName, true);
	}

	public Class reload(Class aClass) throws ClassNotFoundException {
		return loadClass(aClass.getName(), true);
	}

	public synchronized Class loadClass(String name, boolean resolve)
		throws ClassNotFoundException {
			
		if (mutatingCodeAdapter.shouldMutate(name)) {
			return loadAndMutateClass(name);
		}
		else {
			return super.loadClass(name, resolve);
		}
	}
	
	protected synchronized Class loadAndMutateClass(final String name)
		throws ClassNotFoundException {
		
		String classResourceName = name.replace('.', '/') + ".class";

		try {
			ClassReader classReader = new ClassReader(getResourceAsStream(classResourceName));
			ClassWriter classWriter = new ClassWriter(false);
			ClassVisitor classVisitor = new MutatingClassAdapter(classWriter, mutatingCodeAdapter);
			classReader.accept(classVisitor, false);
			byte[] b = classWriter.toByteArray();
			System.out.println("Had no trouble loading: " + classResourceName);
			return defineClass(name, b, 0, b.length);
		} catch (Exception e) {
			System.out.println("For some reason could not load: " + classResourceName);
			e.printStackTrace();
			throw new ClassNotFoundException(name, e);
		}
	}
}