package org.ajester;

import org.objectweb.asm.ClassReader;

import java.io.FileOutputStream;

import junit.runner.TestCaseClassLoader;
import junit.runner.TestSuiteLoader;

public class MutatingClassLoader extends TestCaseClassLoader implements TestSuiteLoader {
	private MutatingClassAdapter mutatingClassAdapter;

	public MutatingClassLoader(MutatingClassAdapter mutatingClassAdapter) {
		this.mutatingClassAdapter = mutatingClassAdapter;
	}
	
	public Class load(String suiteClassName) throws ClassNotFoundException {
		return loadClass(suiteClassName, true);
	}

	public Class reload(Class aClass) throws ClassNotFoundException {
		return loadClass(aClass.getName(), true);
	}

	public synchronized Class loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		if (mutatingClassAdapter.shouldMutate(name)) {
			return loadAndMutateClass(name);
		}
		else if (name.equals(Coverage.class.getName())) {
			return Coverage.class;
		}
		else {
			return super.loadClass(name, resolve);
		}
	}
	
	protected synchronized Class loadAndMutateClass(final String name)
		throws ClassNotFoundException {
		
		String classResourceName = name.replace('.', '/') + ".class";

		try {
			new ClassReader(getResourceAsStream(classResourceName)).accept(mutatingClassAdapter, false);
			byte[] b = mutatingClassAdapter.getClassWriter().toByteArray();
			
			return defineClass(name, b, 0, b.length);
		} catch (Exception e) {
			System.out.println("For some reason could not load: " + classResourceName);
			e.printStackTrace();
			throw new ClassNotFoundException(name, e);
		}
	}
}