package picounit.impl;

public interface DelegatingResultListener extends ResultListener {
	ResultListener setDelegate(ResultListener delegate);
	
	ResultListener getDelegate();
}
