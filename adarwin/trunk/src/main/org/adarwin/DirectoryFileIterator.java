package org.adarwin;

import java.util.LinkedList;
import java.util.List;

public class DirectoryFileIterator implements IFileIterator {
	private final IFileAccessor fileAccessor;
	private List fileStack = new LinkedList();

	public DirectoryFileIterator(String directory, IFileAccessor fileAccessor) {
		this.fileAccessor = fileAccessor;

		if (fileAccessor.isDirectory(directory)) {
			addAll(directory);
		}
		else {
			fileStack.add(directory);
		}
	}

	public boolean hasNext() {
		return !fileStack.isEmpty();
	}

	public String next() {
		String fileName = null;

		while (fileName == null && !fileStack.isEmpty()) {
			fileName = (String) fileStack.remove(0);

			if (!fileAccessor.isDirectory(fileName)) {
				return fileName;
			}
			else {
				addAll(fileName);
				fileName = null;
			}
		}

		return fileName;
	}

	private void addAll(String directory) {
		String[] files = fileAccessor.listFiles(directory);

		for (int fLoop = 0; fLoop < files.length; fLoop++) {
			fileStack.add(files[fLoop]);
		}
	}
}
