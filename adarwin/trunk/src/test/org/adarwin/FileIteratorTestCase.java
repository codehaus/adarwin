package org.adarwin;

import org.easymock.MockControl;

import junit.framework.TestCase;

public class FileIteratorTestCase extends TestCase {
	MockControl fileAccessorControl = MockControl.createNiceControl(IFileAccessor.class);
	IFileAccessor fileAccessor = (IFileAccessor) fileAccessorControl.getMock();
	FileAccessor accessor = new FileAccessor();

	public void testSingleFile() {
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("file"), false);
		fileAccessorControl.replay();

		IFileIterator iterator = new DirectoryFileIterator("file", fileAccessor);
		
		assertTrue(iterator.hasNext());
		assertEquals("file", iterator.next());
		assertFalse(iterator.hasNext());
	}

	public void testDirectoryWithOneFile() {
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("directory"), true);
		fileAccessorControl.expectAndReturn(fileAccessor.listFiles("directory"),
			(new String[] {"file"}));
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("file"), false);

		fileAccessorControl.replay();

		IFileIterator iterator = new DirectoryFileIterator("directory", fileAccessor);

		assertTrue(iterator.hasNext());
		assertEquals("file", iterator.next());
		assertFalse(iterator.hasNext());

		fileAccessorControl.verify();
	}

	public void testDirectoryWithSeveralFiles() {
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("directory"), true);
		fileAccessorControl.expectAndReturn(fileAccessor.listFiles("directory"),
			(new String[] {"file1", "file2"}));
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("file1"), false);
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("file2"), false);

		fileAccessorControl.replay();

		IFileIterator iterator = new DirectoryFileIterator("directory", fileAccessor);

		assertTrue(iterator.hasNext());
		assertEquals("file1", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("file2", iterator.next());
		assertFalse(iterator.hasNext());

		fileAccessorControl.verify();
	}

	public void testNestedDirectories() {
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("directory"), true);
		fileAccessorControl.expectAndReturn(fileAccessor.listFiles("directory"),
			(new String[] {"directory2"}));
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("directory2"), true);
		fileAccessorControl.expectAndReturn(fileAccessor.listFiles("directory2"),
			(new String[] {"file"}));
		fileAccessorControl.expectAndReturn(fileAccessor.isDirectory("file"), false);

		fileAccessorControl.replay();

		IFileIterator iterator = new DirectoryFileIterator("directory", fileAccessor);

		assertTrue(iterator.hasNext());
		assertEquals("file", iterator.next());
		assertFalse(iterator.hasNext());

		fileAccessorControl.verify();
	}
	
	public static void main(String[] args) {
		IFileIterator iterator = new FileAccessor().files(args[0]);

		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}
