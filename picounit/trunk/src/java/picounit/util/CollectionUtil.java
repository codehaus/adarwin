package picounit.util;

import picounit.mocker.easymock.Operation;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {
	public void forEach(Collection collection, Operation operation) {
		for (Iterator controls = collection.iterator(); controls.hasNext(); ) {
			operation.operate(controls.next());
		}
	}
}