package org.ajester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MutatorFactory {
	private final Constructor constructor;

	public MutatorFactory(Class mutatorClass) throws SecurityException, NoSuchMethodException {
		this.constructor = mutatorClass.getConstructor(new Class[] {CodeMatcher.class});
	}
	
	public Mutator createMutator(CodeMatcher codeMatcher) throws IllegalArgumentException,
		InstantiationException, IllegalAccessException, InvocationTargetException {

		return (Mutator) constructor.newInstance(new Object[] {codeMatcher});		
	}
}
