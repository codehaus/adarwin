package org.ajester;


import org.ajester.testmodel.code.BooleanReturn;
import org.ajester.testmodel.code.IfEqualsStatement;
import org.ajester.testmodel.code.ProblematicIfEqualsStatement;
import org.ajester.testmodel.test.BooleanReturnTestCase;
import org.ajester.testmodel.test.IfEqualsStatementTestCase;
import org.ajester.testmodel.test.ProblematicIfStatementTestCase;

import junit.framework.TestCase;

public class AJesterTestCase extends TestCase {
	public void testGetMutatorsReturnsOneMutatorPerMethod() throws Exception {
		Mutator[] mutators = AJester.getMutators(IfEqualsStatementTestCase.class,
			IfEqualsStatement.class, new MutatorFactory(IfStatementMutator.class));

		assertNotNull(mutators);
		
		assertEquals(1, mutators.length);
		
//		Set codeLocations = new HashSet();
		
		for (int mLoop = 0; mLoop < mutators.length; mLoop++) {
			Mutator mutator = mutators[mLoop];

//			codeLocations.add(mutator.getCodeMatcher());

			assertEquals(IfStatementMutator.class, mutator.getClass());
		}

//		assertTrue(codeLocations.contains(IfStatement.IF_EQUAL_LOCATION));
//		assertTrue(codeLocations.contains(IfStatement.IF_NOT_EQUAL_LOCATION));
	}

	public void testMultipleMutators() throws Exception {
		MutatorFactory[] factories = new MutatorFactory[] {
			new MutatorFactory(IfStatementMutator.class),
			new MutatorFactory(BooleanReturnMutator.class)
		};
		
		Mutator[] mutators = AJester.getMutators(IfEqualsStatementTestCase.class, IfEqualsStatement.class,
			factories);
		
		assertNotNull(mutators);
		assertEquals(factories.length, mutators.length);
		
//		Set codeLocations = new HashSet();
		
		for (int mLoop = 0; mLoop < mutators.length; mLoop++) {
			Mutator mutator = mutators[mLoop];

//			codeLocations.add(mutator.getCodeMatcher());

			if (mLoop < 1) {
				assertEquals(IfStatementMutator.class, mutator.getClass());
			}
			else {
				assertEquals(BooleanReturnMutator.class, mutator.getClass());	
			}
		}
		
//		assertTrue(codeLocations.contains(IfStatement.IF_EQUAL_LOCATION));
//		assertTrue(codeLocations.contains(IfStatement.IF_NOT_EQUAL_LOCATION));
	}
	
	public void testReport() throws Exception {
		AJester ajester = new AJester(BooleanReturnTestCase.class,
			new BooleanReturnMutator(BooleanReturn.LOCATION));

		assertEquals(Report.NO_PROBLEMS, ajester.run().getReport());
	}
	
	public void testReportWithAnotherClass() throws Exception {
		AJester ajester = new AJester(IfEqualsStatementTestCase.class,
			new BooleanReturnMutator(IfEqualsStatement.LOCATION));

		assertEquals(Report.NO_PROBLEMS, ajester.run().getReport());
	}

	public void testReportWithProblematicIfStatement() throws Exception {
		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
			new IfStatementMutator(ProblematicIfEqualsStatement.IF_EQUAL_LOCATION));

		String expectedReport = Report.SOME_PROBLEMS + ":\n" +
			"\t" + ProblematicIfEqualsStatement.IF_EQUAL_LOCATION;
		Report report = ajester.run();

		assertEquals(expectedReport, report.getReport());
	}
	
	public void testGetCoverage() throws Exception {
		Report report = new AJester(BooleanReturnTestCase.class,
			new BooleanReturnMutator(BooleanReturn.LOCATION)).run();

		assertTrue(report.getCoverage().contains(BooleanReturn.LOCATION));
	}

	public void testReportWithProblematicIfStatementWhenModifyingDifferentMethod()
		throws Exception {

		CodeLocation codeLocation = new CodeLocation(ProblematicIfEqualsStatement.class,
			"nonExistantMethod");
		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
			new IfStatementMutator(codeLocation));
		assertEquals(Report.NO_PROBLEMS, ajester.run().getReport());
	}

//	public void testReportWithTwoMutations() throws Exception {
//		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
//			new Mutator[] {
//				new IfStatementMutator(ProblematicIfEqualsStatement.IF_EQUAL_LOCATION),
//				new IfStatementMutator(ProblematicIfNotEqualsStatement.IF_NOT_EQUAL_LOCATION)
//			});
//		String expectedReport = Report.SOME_PROBLEMS + ":\n" +
//			"\t" + ProblematicIfEqualsStatement.IF_EQUAL_LOCATION + "\n" +
//			"\t" + ProblematicIfNotEqualsStatement.IF_NOT_EQUAL_LOCATION;
//
//		assertEquals(expectedReport, ajester.run().getReport());
//	}
//	
	public void testNoLocation() throws Exception {
		AJester ajester = new AJester(ProblematicIfStatementTestCase.class,
			ProblematicIfEqualsStatement.class,
			IfStatementMutator.class);

		String expectedReport = Report.SOME_PROBLEMS + ":\n" +
			"\t" + ProblematicIfEqualsStatement.IF_EQUAL_LOCATION;

		assertEquals(expectedReport, ajester.run().getReport());
	}	
}
