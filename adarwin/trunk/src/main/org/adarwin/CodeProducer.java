package org.adarwin;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CodeProducer {
	private final String name;

	public CodeProducer(String name) {
		this.name = name;
	}

	public void produce(CodeConsumer codeConsumer) throws ADarwinException {
		try {
			produce(name, codeConsumer);
		}
		catch (IOException ioException) {
			throw new ADarwinException(ioException);
		}
	}

	private void produce(String name, CodeConsumer codeConsumer) throws ADarwinException, IOException {
		if (isClassPath(name)) {
			StringTokenizer tokenizer = new StringTokenizer(name, File.pathSeparator);

			while(tokenizer.hasMoreTokens()) {
				produce(tokenizer.nextToken(), codeConsumer);
			}
		}
		else if (isClass(name)) {
			codeConsumer.consume(new ClassFile(getFileAccessor().openFile(name)));
		}
		else if (isJar(name)) {
			JarFile jarFile = new JarFile(name);
			
			for (Enumeration entries = jarFile.entries(); entries.hasMoreElements();) {
				JarEntry entry = (JarEntry) entries.nextElement();
			
				if (isClass(entry.getName())) {
					codeConsumer.consume(new ClassFile(jarFile.getInputStream(entry)));
				}
			}
		}
		else if (isDirectory(name)) {
			String[] fileNames = new FileAccessor().listFiles(name);
			
			for (int fLoop = 0; fLoop < fileNames.length; ++fLoop) {
				produce(fileNames[fLoop], codeConsumer);
			}
		}
		else {
			codeConsumer.consume(Code.NULL);
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
		return getFileAccessor().isDirectory(name);
	}
	
	private IFileAccessor getFileAccessor() {
		return new FileAccessor();
	}
}
