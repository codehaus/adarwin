package org.adarwin;

import java.io.FileNotFoundException;

public class ClassDirectoryIterator implements CodeIterator {
	private IFileIterator fileIterator;
	private IFileAccessor fileAccessor;

	public ClassDirectoryIterator(IFileAccessor fileAccessor, IFileIterator fileIterator) {
		this.fileAccessor = fileAccessor;
		this.fileIterator = fileIterator;
	}

	public boolean hasNext() {
		return fileIterator.hasNext();
	}

	public Code next() {
		System.out.println("next");
		try {
			return new ClassFile(fileAccessor.openFile(fileIterator.next()));
		}
		catch (FileNotFoundException e) {
			throw new ADarwinException(e);
		}
	}
}
