package org.ajester;



public class IfStatementMutator extends BaseMutator {
	public IfStatementMutator(CodeMatcher codeMatcher) {
		super(new IfStatementInstructionMutator(codeMatcher));
	}
}
