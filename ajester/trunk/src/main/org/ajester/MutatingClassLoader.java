package org.ajester;

import org.objectweb.asm.ClassReader;

import junit.runner.TestCaseClassLoader;
import junit.runner.TestSuiteLoader;

public class MutatingClassLoader extends TestCaseClassLoader implements TestSuiteLoader {
	private final Mutator mutator;

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

		boolean newBoolean = !className.startsWith("java") && !className.startsWith("junit");
		boolean oldBoolean = mutator != null && mutator.getCodeMatcher().matches(className);
		
		if (newBoolean != oldBoolean) {
			System.out.println("new ain't gonna work with: " + className);
		}
		
		if (className.equals(Coverage.class.getName())) {
			//System.out.println("Not mutating Coverage");
			return Coverage.class;
		}
		//else if (newBoolean && !className.equals("org.ajester.CodeMatcher")) {
		else if (oldBoolean) {
//			System.out.println("mutating: " + className + ", with: " + mutator);
			return loadAndMutateClass(mutator, className);
		}
		else {
			return super.loadClass(className, resolve);
		}
	}

	private synchronized Class loadAndMutateClass(Mutator mutator, final String className)
		throws ClassNotFoundException {

		String classResourceName = className.replace('.', '/') + ".class";

		try {
//			System.out.println("loadAndMutateClass: " + className);
			byte[] b = mutator.visit(new ClassReader(getResourceAsStream(classResourceName)));
//			System.out.println(className + ".size = " + b.length);
			return defineClass(className, b, 0, b.length);
		} catch (Exception e) {
			System.out.println("For some reason could not load: " + classResourceName);
			e.printStackTrace();
			throw new ClassNotFoundException(className, e);
		}
//		catch (Throwable t) {
//			System.out.println("Something bejigered with: " + className + " (" + mutator + ")");
//			t.printStackTrace(System.out);
//			return null;
//		}
	}
}