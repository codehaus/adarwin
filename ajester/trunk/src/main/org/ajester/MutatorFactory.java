package org.ajester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MutatorFactory {
	private final Constructor constructor;

	public MutatorFactory(Class mutatorClass) throws SecurityException, NoSuchMethodException {
		this.constructor = mutatorClass.getConstructor(new Class[] {CodeMatcher.class});
	}
	
	public InstructionMutator createMutator(CodeMatcher codeMatcher) throws IllegalArgumentException,
		InstantiationException, IllegalAccessException, InvocationTargetException {

		return (InstructionMutator) constructor.newInstance(new Object[] {codeMatcher});		
	}
}
