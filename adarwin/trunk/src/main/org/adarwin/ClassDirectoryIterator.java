package org.adarwin;

import java.io.FileNotFoundException;

public class ClassDirectoryIterator implements CodeIterator {
	private final IFileIterator fileIterator;
	private final IFileAccessor fileAccessor;
	private ClassSummary next;

	public ClassDirectoryIterator(IFileAccessor fileAccessor, IFileIterator fileIterator) {
		this.fileAccessor = fileAccessor;
		this.fileIterator = fileIterator;

		next = prefetch();
	}

	public boolean hasNext() {
		return next != null;
	}

	public ClassSummary next() {
		ClassSummary toReturn = next;

		if (next != null) {
			next = prefetch();
		}

		return toReturn;
	}

	private ClassSummary prefetch() {
		if (!fileIterator.hasNext()) {
			return null;
		}

		try {
			String fileName;

			for(fileName = fileIterator.next(); !CodeProducer.isClass(fileName);) {
				if (!fileIterator.hasNext()) {
					return null;
				}

				fileName = fileIterator.next();
			}

			return RuleClassVisitor.visit(fileAccessor.openFile(fileName));
		}
		catch (FileNotFoundException e) {
			throw new ADarwinException(e);
		}
	}
}
