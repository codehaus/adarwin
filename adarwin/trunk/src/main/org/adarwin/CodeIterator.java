package org.adarwin;

public interface CodeIterator {
	CodeIterator NULL = new CodeIterator() {
		public boolean hasNext() {
			return false;
		}
		public ClassSummary next() {
			return null;
		}
	};

	public boolean hasNext();

	public ClassSummary next();
}
