package org.adarwin;

import java.io.File;
import java.util.StringTokenizer;

public class ClassPathIterator implements CodeIterator {
	private final StringTokenizer tokenizer;
	private final IFileAccessor fileAccessor;
	private CodeIterator iterator;

	public ClassPathIterator(String classPath, IFileAccessor fileAccessor) {
		this.fileAccessor = fileAccessor;
		this.tokenizer = new StringTokenizer(classPath, File.pathSeparator);
	}

	public boolean hasNext() {
		return tokenizer.hasMoreTokens() || (iterator != null && iterator.hasNext());
	}

	public ClassSummary next() {
		if ((iterator == null || !iterator.hasNext()) && tokenizer.hasMoreTokens()) {
			iterator = new CodeProducer(tokenizer.nextToken(), fileAccessor).iterator();
		}

		if (iterator != null && iterator.hasNext()) {
			return iterator.next();
		}

		return null;
	}
}
