package picounit.impl;

import java.util.List;

public class NothingFilter implements Filter {
	public boolean passes(List callStack) {
		return false;
	}
}
