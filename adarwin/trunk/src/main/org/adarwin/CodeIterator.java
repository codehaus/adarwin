package org.adarwin;

public interface CodeIterator {
	CodeIterator NULL = new CodeIterator() {
		public boolean hasNext() {
			return false;
		}
		public Code next() {
			return null;
		}
	};

	public boolean hasNext();

	public Code next();
}
