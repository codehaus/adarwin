package org.ajester;

public interface InstructionMatcher {
	public CodeMatcher getCodeMatcher();
	
	public boolean matches(Instruction instructionType);
}
