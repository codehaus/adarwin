package org.ajester;

import org.objectweb.asm.ClassReader;

import java.io.IOException;

import junit.runner.TestCaseClassLoader;
import junit.runner.TestSuiteLoader;

public class MutatingClassLoader extends TestCaseClassLoader implements TestSuiteLoader {
	private static final String[] PROHIBITED_CLASSES = new String[] {
		"java", "junit", "org.ajester.CodeMatcher", "org.ajester.testmodel.test" };

	private final BaseMutator mutator;

	public MutatingClassLoader(InstructionMutator instructionMutator) {
		this.mutator = new BaseMutator(instructionMutator);
	}

	public Class load(String suiteClassName) throws ClassNotFoundException {
		return loadClass(suiteClassName, true);
	}

	public Class reload(Class aClass) throws ClassNotFoundException {
		return loadClass(aClass.getName(), true);
	}

	public synchronized Class loadClass(String className, boolean resolve)
		throws ClassNotFoundException {

		if (className.equals(Coverage.class.getName())) {
			//System.out.println("Not mutating Coverage");
			return Coverage.class;
		}
		else if (!startsWith(className, PROHIBITED_CLASSES)) {
//			System.out.println("mutating: " + className + ", with: " + mutator);
			return loadAndMutateClass(mutator, className);
		}
		else {
			return super.loadClass(className, resolve);
		}
	}

	private boolean startsWith(String searchIn, String[] searchFor) {
		for (int sLoop = 0; sLoop < searchFor.length; sLoop++) {
			if (searchIn.startsWith(searchFor[sLoop])) {
				return true;
			}
		}
		
		return false;
	}

	private synchronized Class loadAndMutateClass(BaseMutator mutator, final String className)
		throws ClassNotFoundException {

		String classResourceName = className.replace('.', '/') + ".class";

		try {
//			System.out.println("loadAndMutateClass: " + className);
			byte[] b = visit(mutator, classResourceName);
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

	private byte[] visit(BaseMutator mutator, String classResourceName) throws IOException {
		ClassReader reader = new ClassReader(getResourceAsStream(classResourceName));
		
		mutator.getClassWriter().reset();
		reader.accept(mutator, false);
		return mutator.getClassWriter().toByteArray();
	}
}