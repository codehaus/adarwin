package org.ajester;

public interface InstructionMutator extends InstructionMatcher {
	public Instruction mutate(Instruction instruction);
}
