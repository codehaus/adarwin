/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin.testmodel.a;

import org.adarwin.testmodel.b.InPackageB;
import org.adarwin.testmodel.c.InPackageC;

public class UsesPackageBAndPackageC {
	public void uses() {
		new InPackageB();
		new InPackageC();
	}
}
