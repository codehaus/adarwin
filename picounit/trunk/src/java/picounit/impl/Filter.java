package picounit.impl;

import java.util.List;

public interface Filter {
	boolean passes(List callStack);
}
