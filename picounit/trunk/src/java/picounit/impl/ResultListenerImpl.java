package picounit.impl;

public class ResultListenerImpl implements DelegatingResultListener {
	private ResultListener delegate;

	public ResultListenerImpl(ResultListener delegate) {
		this.delegate = delegate;
	}
	
	public ResultListener setDelegate(ResultListener delegate) {
//		System.out.println("\nsetDelegate: " + delegate.getClass());
		
		ResultListener previous = this.delegate;
		
		this.delegate = delegate;
		
		return previous;
	}
	
	public ResultListener getDelegate() {
		return delegate;
	}
	
	public void enter(Scope scope) {
		delegate.enter(scope);
	}

	public void exit() {
		delegate.exit();
	}

	public void exit(Throwable throwable) {
		delegate.exit(throwable);
	}
}
