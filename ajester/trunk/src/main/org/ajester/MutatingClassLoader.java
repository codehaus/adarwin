package org.ajester;

import org.objectweb.asm.ClassReader;

import junit.runner.TestCaseClassLoader;
import junit.runner.TestSuiteLoader;

public class MutatingClassLoader extends TestCaseClassLoader implements TestSuiteLoader {
	private Mutator mutator;

	public MutatingClassLoader(Mutator mutator) {
		this.mutator = mutator;
	}

	public Class load(String suiteClassName) throws ClassNotFoundException {
		return loadClass(suiteClassName, true);
	}

	public Class reload(Class aClass) throws ClassNotFoundException {
		return loadClass(aClass.getName(), true);
	}

	public synchronized Class loadClass(String className, boolean resolve)
		throws ClassNotFoundException {
		
		if (mutator != null && mutator.shouldMutate(className)) {
			return loadAndMutateClass(mutator, className);
		}
		else if (className.equals(Coverage.class.getName())) {
			return Coverage.class;
		}
		else {
			return super.loadClass(className, resolve);
		}
	}

	private synchronized Class loadAndMutateClass(Mutator mutator, final String className)
		throws ClassNotFoundException {
		
		String classResourceName = className.replace('.', '/') + ".class";

		try {
			new ClassReader(getResourceAsStream(classResourceName)).accept(mutator, false);
			byte[] b = mutator.getClassWriter().toByteArray();
			
			return defineClass(className, b, 0, b.length);
		} catch (Exception e) {
			System.out.println("For some reason could not load: " + classResourceName);
			e.printStackTrace();
			throw new ClassNotFoundException(className, e);
		}
	}
}