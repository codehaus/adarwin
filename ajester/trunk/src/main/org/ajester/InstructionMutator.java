package org.ajester;

import java.util.Set;

public interface InstructionMutator {
	public CodeMatcher getCodeMatcher();

	public boolean matches(Instruction instructionType);

	public Instruction mutate(Instruction instruction);
	
	public Set getMutations();
}
