/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin;

import com.mockobjects.dynamic.Mock;

import java.io.FileNotFoundException;

import junit.framework.TestCase;

public class ClassPathTestCase extends TestCase {
	public void testPathParsing() throws FileNotFoundException {
		String fileName = "test.class";
		Mock mockCodeFactory = new Mock(ICodeFactory.class);
		mockCodeFactory.expect("create", fileName);
		ICodeFactory codeFactory = (ICodeFactory) mockCodeFactory.proxy();

		ClassPath.getCodePath(codeFactory, fileName);

		mockCodeFactory.verify();
	}
}
