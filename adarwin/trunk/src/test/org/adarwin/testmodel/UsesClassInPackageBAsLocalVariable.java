/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin.testmodel;

import org.adarwin.testmodel.b.InPackageB;

public class UsesClassInPackageBAsLocalVariable {
    public void naughtyMethod() {
        InPackageB prohibited = null;
    }
}
