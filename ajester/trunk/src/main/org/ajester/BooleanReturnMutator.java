package org.ajester;


public class BooleanReturnMutator extends BaseMutator {
	public BooleanReturnMutator(CodeMatcher codeMatcher) {
		super(new BooleanReturnMatcher(codeMatcher), new BooleanReturnInstructionMutator());
	}
}
