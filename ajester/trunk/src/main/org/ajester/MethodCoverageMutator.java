package org.ajester;


public class MethodCoverageMutator extends BaseMutator {
	public MethodCoverageMutator(CodeMatcher codeMatcher) {
		super(new MethodCoverageInstructionMutator(codeMatcher));
	}
}
