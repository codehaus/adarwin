package org.adarwin;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarIterator implements CodeIterator {
	private final JarFile jarFile;
	private final Enumeration entries;

	public JarIterator(String jar, IFileAccessor fileAccessor) {
		try {
			this.jarFile = new JarFile(jar);
			this.entries = jarFile.entries();
		} catch (IOException e) {
			throw new ADarwinException(e);
		}
	}

	public boolean hasNext() {
		return entries.hasMoreElements();
	}

	public Code next() {
		JarEntry entry = (JarEntry) entries.nextElement();
		
		if (CodeProducer.isClass(entry.getName())) {
			try {
				return new ClassFile(jarFile.getInputStream(entry));
			} catch (IOException e) {
				throw new ADarwinException(e);
			}
		}
		else {
			return Code.NULL;
		}
	}
}
