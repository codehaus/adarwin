package picounit.impl;

import java.util.List;

public class EveryThingFilter implements Filter {
	public boolean passes(List callStack) {
		return true;
	}
}
