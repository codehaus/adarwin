package org.ajester;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AJester {
	private String testClassName;
	private Mutator mutator;
	private Mutator[] mutators;
	
	public static Mutator[] getMutators(Class testCaseClass, Class codeClass,
		MutatorFactory mutatorFactory) throws Exception {
		
		return getMutators(testCaseClass, codeClass, new MutatorFactory[] {mutatorFactory});
	}

	public static Mutator[] getMutators(Class testCaseClass, Class codeClass,
		MutatorFactory[] factories) throws Exception {
		
		Report report = new TestRunnerWrapper().run(testCaseClass,
			new MethodCoverageMutator(new ClassCodeMatcher(codeClass.getName())));

		List mutators = new LinkedList();
		
		for (int fLoop = 0; fLoop < factories.length; fLoop++) {
			Coverage coverage = report.getCoverage();
			for(Iterator iterator = coverage.getCoverage().iterator(); iterator.hasNext();) {
				CodeLocation codeLocation = (CodeLocation) iterator.next();
			
				mutators.add(factories[fLoop].createMutator(codeLocation));
			}			
		}
		
		return (Mutator[]) mutators.toArray(new Mutator[0]);
	}
	
	public AJester(Class testCaseClass, Mutator mutator) {
		this.testClassName = testCaseClass.getName();
		this.mutator = mutator;
	}

	public AJester(Class testCaseClass, Mutator[] mutators) {
		this.testClassName = testCaseClass.getName();
		this.mutators = mutators;
	}

	public AJester(Class testCaseClass, Class codeClass, Class mutatorClass) throws Exception {
		this.testClassName = testCaseClass.getName();
		this.mutators = getMutators(testCaseClass, codeClass, new MutatorFactory(mutatorClass));
	}

	public Report run() throws Exception {
		if (mutator != null) {
			return runOne(mutator);
		}
		else {
			Report[] reports = new Report[mutators.length];
			for (int mLoop = 0; mLoop < mutators.length; mLoop++) {
				reports[mLoop] = runOne(mutators[mLoop]);
			}

			return aggregateReports(reports);
		}
	}

	private Report runOne(Mutator mutator) throws Exception {
		TestRunnerWrapper runnerWrapper = new TestRunnerWrapper();
		return runnerWrapper.run(testClassName, mutator);
	}

	private Report aggregateReports(Report[] reports) {
		Set methodsCalled = new HashSet();
		Map callerCalledMap = new HashMap();
		Map calledCallerMap = new HashMap();
		Set failures = new HashSet();
		Set errors = new HashSet();
		Set mutations = new HashSet();
		
		for (int rLoop = 0; rLoop < reports.length; rLoop++) {
			Report report = reports[rLoop];
			failures.addAll(report.getFailures());
			errors.addAll(report.getErrors());
			mutations.addAll(report.getMutations());
			Coverage coverage = report.getCoverage();
			methodsCalled.addAll(coverage.getCoverage());
			callerCalledMap.putAll(coverage.getCallerCalledMap());
			calledCallerMap.putAll(coverage.getCalledCallerMap());
		}
		
		return new Report(failures, errors, mutations, new Coverage(methodsCalled, callerCalledMap,
			calledCallerMap));
	}
}
