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

import org.adarwin.rule.Rule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.b.ExceptionInPackageB;
import org.adarwin.testmodel.b.IInPackageB;
import org.adarwin.testmodel.b.InPackageB;

import java.io.IOException;

public class UsesPackageTestCase extends RuleTestCase {
    private final Rule rule = new UsesRule(createPackageRule(InPackageB.class));

    public void testNotUsingPackage() throws ADarwinException {
		assertNumMatches(0, rule, String.class);
    }    

	public void testUsesAsField() throws ADarwinException {
		class UsesClassInPackageBAsField {
		    InPackageB empty;
		}

		assertNumMatches(1, rule, UsesClassInPackageBAsField.class);
    }

    public void testUsesAsLocalVariable() throws ADarwinException {
    	class UsesClassInPackageBAsLocalVariable {
    	    public void naughtyMethod() {
    	        InPackageB prohibited = null;
    	    }
    	}

    	assertNumMatches(1, rule, UsesClassInPackageBAsLocalVariable.class);
    }

    public void testUsesAsAnonymouseLocalVariable() throws ADarwinException {
    	class UsesClassInPackageBAsAnonymousLocalVariable {
    	    public void naughty() {
    	        new InPackageB();
    	    }
    	}

    	assertNumMatches(1, rule, UsesClassInPackageBAsAnonymousLocalVariable.class);
    }

    public void testUsesForInvocation() throws ADarwinException {
    	class UsesClassInPackageBForInvocation {
    	    public void naughty() {
    	        new InPackageB().empty();
    	    }
    	}

    	assertNumMatches(1, rule, UsesClassInPackageBForInvocation.class);
    }

    public void testUsesForStaticInvocation() throws ADarwinException {
    	class UsesClassInPackageBForStaticInvocation {
    	    public void naughty() {
    	        InPackageB.staticEmpty();
    	    }
    	}

    	assertNumMatches(1, rule, UsesClassInPackageBForStaticInvocation.class);
    }

    public void testUsesForClassInstanceField() throws ADarwinException {
    	class UsesClassInPackageBForClassInstanceField {
    	    Class naughtyClass = InPackageB.class;
    	}

    	assertNumMatches(1, rule, UsesClassInPackageBForClassInstanceField.class);
    }
    
	static class UsesClassInStaticReturnValue {
		static InPackageB naughty() {
			return null;
		}
	}

	public void testUsesForStaticReturnValue() throws ADarwinException {
		assertNumMatches(1, rule, UsesClassInStaticReturnValue.class);
    }

    public void testUsesForClassInstanceInMethod() throws ADarwinException {
    	class UsesClassInPackageBForClassInstanceInMethod {
    	    public void naughty() {
    	        System.out.println(InPackageB.class.getName());
    	    }
    	}

    	assertNumMatches(1, rule, UsesClassInPackageBForClassInstanceInMethod.class);
    }

	public void testUsesForBaseClass() throws ADarwinException {
		class UsesClassForBaseClass extends InPackageB {
		}
		
		assertNumMatches(1, rule, UsesClassForBaseClass.class);
	}

	public void testUsesForBaseInterface() throws ADarwinException {
		assertNumMatches(1, rule, IUsesClassForBaseInterface.class);
	}

	public void testUsesForInterfaceImplementation() throws ADarwinException {
		class UsesClassForBaseInterface implements IInPackageB {
		}
		
		assertNumMatches(1, rule, UsesClassForBaseInterface.class);
	}
	
	public void testUsesInMethodReturn() throws ADarwinException {
		class UsesClassInPackageBInMethodReturn {
			public InPackageB method() {
				return null;
			}
		}

		assertNumMatches(1, rule, UsesClassInPackageBInMethodReturn.class);
	}

	public void testUsesInMethodParameters() throws ADarwinException {
		class UsesClassInPackageBInMethodParameters {
			public void method(InPackageB naughty) {
			}
		}

		assertNumMatches(1, rule, UsesClassInPackageBInMethodParameters.class);
	}
	
	public void testUsesInConstructorParameters() throws ADarwinException {
		class UsesClassInPackageBInConstructorParameters {
			public UsesClassInPackageBInConstructorParameters(
				InPackageB naughty) {
			}
		}

		assertNumMatches(1, rule, UsesClassInPackageBInConstructorParameters.class);
	}

	public void testUsesInMethodThrowsClause() throws ADarwinException {
		class UsesClassInPackageBInMethodThrowsClause {
			public void naughty() throws ExceptionInPackageB {
			}
		}

		assertNumMatches(1, rule, UsesClassInPackageBInMethodThrowsClause.class);
	}

	public void testUsesInThrow() throws ADarwinException {
		class UsesClassInPackageBInThrow {
			public void naughty() {
				throw new ExceptionInPackageB();
			}
		}

		assertNumMatches(1, rule, UsesClassInPackageBInThrow.class);
	}
	
	interface IUsesClassForBaseInterface extends IInPackageB {
	}

	// Rewrite this to use CodeProducer
//	public void testTwoClassesUsing() throws IOException, ADarwinException {
//		class UsesClassForBaseInterface implements IInPackageB {
//		}
//
//		assertNumMatches(2, rule, JarFileTestCase.createJarFile(new Class[] {
//			UsesClassForBaseInterface.class, IUsesClassForBaseInterface.class}
//		));
//	}
}
