package org.adarwin;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CodeProducer {
	private final String name;
	private final IFileAccessor fileAccessor;

	public CodeProducer(String name, IFileAccessor fileAccessor) {
		this.name = name;
		this.fileAccessor = fileAccessor;
	}

	public void produce(CodeConsumer codeConsumer) {
		try {
			produce(name, codeConsumer);
		}
		catch (IOException ioException) {
			throw new ADarwinException(ioException);
		}
	}

	private void produce(String name, CodeConsumer codeConsumer) throws IOException {
		if (isClassPath(name)) {
			produceClassPath(name, codeConsumer);
		}
		else if (isClass(name)) {
			codeConsumer.consume(new ClassFile(fileAccessor.openFile(name)));
		}
		else if (isJar(name)) {
			produceJar(name, codeConsumer);
		}
		else if (isDirectory(name)) {
			produceDirectory(name, codeConsumer);
		}
	}

	private void produceClassPath(String name, CodeConsumer codeConsumer) throws IOException {
		StringTokenizer tokenizer = new StringTokenizer(name, File.pathSeparator);

		while(tokenizer.hasMoreTokens()) {
			produce(tokenizer.nextToken(), codeConsumer);
		}
	}

	private void produceJar(String name, CodeConsumer codeConsumer) throws IOException {
		JarFile jarFile = new JarFile(name);

		for (Enumeration entries = jarFile.entries(); entries.hasMoreElements();) {
			JarEntry entry = (JarEntry) entries.nextElement();
		
			if (isClass(entry.getName())) {
				codeConsumer.consume(new ClassFile(jarFile.getInputStream(entry)));
			}
		}
	}

	private void produceDirectory(String name, CodeConsumer codeConsumer) throws IOException {
		String[] fileNames = new FileAccessor().listFiles(name);

		for (int fLoop = 0; fLoop < fileNames.length; ++fLoop) {
			produce(fileNames[fLoop], codeConsumer);
		}
	}

	private boolean isClassPath(String name) {
		return name.indexOf(File.pathSeparator) != -1;
	}

	private boolean isClass(String token) {
		return token.endsWith(".class");
	}

	private boolean isJar(String token) {
		return token.endsWith(".jar");
	}

	private boolean isDirectory(String name) {
		return fileAccessor.isDirectory(name);
	}
}
