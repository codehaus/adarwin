package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;
import org.ajester.testmodel.IfStatement;
import org.ajester.testmodel.IfStatementTestCase;
import org.ajester.testmodel.ProblematicIfStatement;
import org.ajester.testmodel.ProblematicIfStatementTestCase;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class AJesterTestCase extends TestCase {
	private static final int NUM_METHODS_IN_IF_STATEMENT_CLASS = 2;
	
	public void testGetMutatorsReturnsOneMutatorPerMethod() throws Exception {
		Mutator[] mutators = AJester.getMutators(IfStatementTestCase.class,
			IfStatement.class, new MutatorFactory(IfStatementMutator.class));

		assertNotNull(mutators);
		
		for (int mLoop = 0; mLoop < mutators.length; mLoop++) {
			System.out.println("-- " + mutators[mLoop].getCodeMatcher());
		}
		
		assertEquals(NUM_METHODS_IN_IF_STATEMENT_CLASS, mutators.length);
		
		Set codeLocations = new HashSet();
		
		for (int mLoop = 0; mLoop < mutators.length; mLoop++) {
			Mutator mutator = mutators[mLoop];

			codeLocations.add(mutator.getCodeMatcher());

			assertEquals(IfStatementMutator.class, mutator.getClass());
		}
		
		assertTrue(codeLocations.contains(IfStatement.IF_EQUAL_LOCATION));
		assertTrue(codeLocations.contains(IfStatement.IF_NOT_EQUAL_LOCATION));
	}

	public void testMultipleMutators() throws Exception {
		MutatorFactory[] factories = new MutatorFactory[] {
			new MutatorFactory(IfStatementMutator.class),
			new MutatorFactory(BooleanReturnMutator.class)
		};
		
		Mutator[] mutators = AJester.getMutators(IfStatementTestCase.class, IfStatement.class,
			factories);
		
		assertNotNull(mutators);
		assertEquals(NUM_METHODS_IN_IF_STATEMENT_CLASS * factories.length, mutators.length);
		
		Set codeLocations = new HashSet();
		
		for (int mLoop = 0; mLoop < mutators.length; mLoop++) {
			Mutator mutator = mutators[mLoop];

			codeLocations.add(mutator.getCodeMatcher());

			if (mLoop < NUM_METHODS_IN_IF_STATEMENT_CLASS) {
				assertEquals(IfStatementMutator.class, mutator.getClass());
			}
			else {
				assertEquals(BooleanReturnMutator.class, mutator.getClass());	
			}
		}
		
		assertTrue(codeLocations.contains(IfStatement.IF_EQUAL_LOCATION));
		assertTrue(codeLocations.contains(IfStatement.IF_NOT_EQUAL_LOCATION));
	}
	
	public void testReport() throws Exception {
		AJester ajester = new AJester(BooleanReturnTestCase.class,
			new BooleanReturnMutator(BooleanReturn.GET_TRUE_LOCATION));

		assertEquals(Report.NO_PROBLEMS, ajester.run().getReport());
	}
	
	public void testReportWithAnotherClass() throws Exception {
		AJester ajester = new AJester(IfStatementTestCase.class,
			new BooleanReturnMutator(IfStatement.IF_EQUAL_LOCATION));

		assertEquals(Report.NO_PROBLEMS, ajester.run().getReport());
	}

	public void testReportWithProblematicIfStatement() throws Exception {
		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
			new IfStatementMutator(ProblematicIfStatement.IF_EQUAL_LOCATION));

		String expectedReport = Report.SOME_PROBLEMS + ":\n" +
			"\t" + ProblematicIfStatement.IF_EQUAL_LOCATION;
		Report report = ajester.run();

		assertEquals(expectedReport, report.getReport());
	}
	
	public void testGetCoverage() throws Exception {
		Report report = new AJester(BooleanReturnTestCase.class,
			new BooleanReturnMutator(BooleanReturn.GET_TRUE_LOCATION)).run();

		assertTrue(report.getCoverage().contains(BooleanReturn.GET_TRUE_LOCATION));
	}

	public void testReportWithProblematicIfStatementWhenModifyingDifferentMethod()
		throws Exception {

		CodeLocation codeLocation = new CodeLocation(ProblematicIfStatement.class,
			"nonExistantMethod");
		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
			new IfStatementMutator(codeLocation));
		assertEquals(Report.NO_PROBLEMS, ajester.run().getReport());
	}

	public void testReportWithTwoMutations() throws Exception {
		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
			new Mutator[] {
				new IfStatementMutator(ProblematicIfStatement.IF_EQUAL_LOCATION),
				new IfStatementMutator(ProblematicIfStatement.IF_NOT_EQUAL_LOCATION)
			});
		String expectedReport = Report.SOME_PROBLEMS + ":\n" +
			"\t" + ProblematicIfStatement.IF_EQUAL_LOCATION + "\n" +
			"\t" + ProblematicIfStatement.IF_NOT_EQUAL_LOCATION;

		assertEquals(expectedReport, ajester.run().getReport());
	}
	
	public void testNoLocation() throws Exception {
		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
			ProblematicIfStatement.class,
			IfStatementMutator.class);

		String expectedReport = Report.SOME_PROBLEMS + ":\n" +
			"\t" + ProblematicIfStatement.IF_EQUAL_LOCATION + "\n" +
			"\t" + ProblematicIfStatement.IF_NOT_EQUAL_LOCATION;

		assertEquals(expectedReport, ajester.run().getReport());
	}	
}
