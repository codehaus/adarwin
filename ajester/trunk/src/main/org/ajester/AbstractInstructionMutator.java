package org.ajester;

import java.util.HashSet;
import java.util.Set;

abstract public class AbstractInstructionMutator implements InstructionMutator {
	private final CodeMatcher codeMatcher;
	private final Set mutations;

	public AbstractInstructionMutator(CodeMatcher codeMatcher) {
		this.codeMatcher = codeMatcher;
		this.mutations = new HashSet();
	}
	
	public final CodeMatcher getCodeMatcher() {
		return codeMatcher;
	}
	
	public final Instruction mutate(Instruction instruction) {
		addCovered(instruction);
		
		return mutateImpl(instruction);
	}

	public Set getMutations() {
		return mutations;
	}

	abstract protected Instruction mutateImpl(Instruction instruction);

	private void addCovered(Instruction instruction) {
		getMutations().add(instruction.getCodeLocation());
	}
}
