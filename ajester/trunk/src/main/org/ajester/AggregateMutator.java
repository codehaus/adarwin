package org.ajester;

public class AggregateMutator extends Mutator {
	private Mutator booleanReturnMutator;
	private Mutator ifStatementMutator;

	public AggregateMutator(CodeLocation codeLocation) {
		super(codeLocation);
	}

	public AggregateMutator(Mutator booleanReturnMutator, Mutator ifStatementMutator) {
		super(null);
		this.ifStatementMutator = ifStatementMutator;
		this.booleanReturnMutator = booleanReturnMutator;
	}
}
