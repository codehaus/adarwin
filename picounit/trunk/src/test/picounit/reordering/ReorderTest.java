package picounit.reordering;

import picounit.Test;
import picounit.Verify;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class ReorderTest implements Test {
	private final Verify verify;

	public ReorderTest(Verify verify) {
		this.verify = verify;
	}

	public void testReorderEmptyListDoesNothing() {
		verify.equal(list(list()), reorder(list(), list()));
	}

	// [123] -> [123] = [123]
	public void testReorder123to123() {
		verify.equal(list(list("1 2 3")), reorder(list("1 2 3"), list("1 2 3")));
	}

	// [123] -> [12] = [12]
	public void testReorder123to12() {
		verify.equal(list(list("1 2")), reorder(list("1 2 3"), list("1 2")));
	}

	// [123] -> [21] = [[2] [1]]
	public void testReorder123to21() {
		verify.equal(list(list("2"), list("1")), reorder(list("1 2 3"), list("2 1")));
	}

	// [123] -> [321] = [[3] [2] [1]]
	public void testReorder123to321() {
		verify.equal(list(list("3"), list("2"), list("1")), reorder(list("1 2 3"), list("3 2 1")));
	}

	// [1234] -> [3412] = [[12] [34]]
	public void testReorder1234to3412() {
		verify.equal(list(list("3 4"), list("1 2")), reorder(list("1 2 3 4"), list("3 4 1 2")));
	}

	// [123456] -> [135246] = [[135] [246]]
	public void testReorder123456to135246() {
		verify.equal(list(list("1 3 5"), list("2 4 6")), reorder(list("1 2 3 4 5 6"), list("1 3 5 2 4 6")));
	}
	
	// [SA[TA] SB[TB]] -> [SA[TA] SB[TB]] = [SA[TA] SB[TB]] 
	public void testReorderTuple() {
		verify.equal(list(list("SuiteA.TestA SuiteB.TestB")),
			reorder(list("SuiteA.TestA SuiteB.TestB"), list("SuiteA.TestA SuiteB.TestB")));
	}

	// [SA[TA] SB[TB]] -> [SB[TB] SA[TA]] = [[SB[TB]] [SA[TA]]]
	public void testReorderTuple2() {
		verify.equal(list(list("SuiteB.TestB"), list("SuiteA.TestA")),
			reorder(list("SuiteA.TestA SuiteB.TestB"), list("SuiteB.TestB SuiteA.TestA")));
	}

	public void testReorderTuple3() {
		verify.equal(list(list("SA.T1 SB.T2"), list("SA.T2 SB.T3")),
			reorder(list("SA.T1 SA.T2 SA.T3 SB.T1 SB.T2 SB.T3"), list("SA.T1 SB.T2 SA.T2 SB.T3")));
	}

	// new list means 'pop entire context', how can I represent popping part of the context ?
	// how do I represent the context ?
	//  
	// A
	//   G
	//     123
	//   H
	//     456
	
	// A.setup
	//   G.setup
	//      123
	//   G.teardown
	//   H.setup
	//     456
	//   H.teardown
	// A.teardown
	
	// AG1 AG3 AH6 AH4 ->
	// A.setup
	//   G.setup
	//      13
	//   G.teardown
	//   H.setup
	//      6
	//   H.teardown
	//   H.setup
	//     4
	//   H.teardown
	// A.teardown

	// [A[G[123] H[456]]] -> [A[G[13] H[64]]] = [A[G[13] H[6] H[4]]] 
	public void testReorderTuple4() {
		verify.equal(list(list("A.G.1  A.G.3  A.H.6"), list("A.H.4")),
			reorder(list("A.G.1  A.G.2  A.G.3  A.H.4  A.H.5  A.H.6"), 
				list("A.G.1  A.G.3  A.H.6  A.H.4")));
	}
	
	// [A[G[123] H[456]]] -> [A[G[13] H[64] G[2]]] = [A[G[13] H[6] H[4]] A[G[2]]]
	
	// Iterate into hierarchy, when in context discover element prior to previous replace context
	// with context contain elements already visited plus context with remaining elements,
	// continue with second context

	private List list() {
		return new LinkedList();
	}

	private List list(Object first) {
		List list = list();
		add(list, first);
		return list;
	}

	private List list(Object first, Object second) {
		List list = list(first);
		add(list, second);
		return list;
	}
	
	private List list(Object first, Object second, Object third) {
		List list = list(first, second);
		add(list, third);
		return list;
	}

	private void add(List list, Object element) {
		if (element instanceof String) {
			String string = (String) element;
			StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
			while(stringTokenizer.hasMoreTokens()) {
				String token = stringTokenizer.nextToken();
				list.add(new Tuple(token));
			}
		}
		else {
			list.add(element);
		}
	}

	private List reorder(List initialList, List desiredList) {
		List collector = list();
		List currentList = list();
		collector.add(currentList);

		int currentIndex = -1;

		for (Iterator index = desiredList.iterator(); index.hasNext(); ) {
			Object element = index.next();

			int nextIndex = initialList.indexOf(element);
			
			if (nextIndex < currentIndex) {
				collector.add(currentList = list());
			}
			
			currentIndex = nextIndex; 
			currentList.add(element);
		}

		return collector;
	}
		
	// 1.2.3
	private static final class Tuple {
		private final String data;

		public Tuple(String data) {
			this.data = data;
		}

		public boolean equals(Object object) {
			return object != null && object instanceof Tuple &&
				((Tuple)object).data.equals(data);
		}
		
		public String toString() {
			return "{" + data + "}";
		}
	}
}
