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

public class UsesPackageTestCase extends RuleTestCase {
    private final Rule rule = new UsesRule(createPackageRule(InPackageB.class));

    public void testNotUsingPackage() {
    	assertFalse(matches(rule, String.class));
    }    

	public void testUsesAsField() {
		class UsesClassInPackageBAsField {
		    InPackageB empty;
		}

		assertTrue(matches(rule, UsesClassInPackageBAsField.class));
    }

    public void testUsesAsLocalVariable() {
    	class UsesClassInPackageBAsLocalVariable {
    	    public void naughtyMethod() {
    	        InPackageB prohibited = null;
    	    }
    	}

    	assertTrue(matches(rule, UsesClassInPackageBAsLocalVariable.class));
    }

    public void testUsesAsAnonymouseLocalVariable() {
    	class UsesClassInPackageBAsAnonymousLocalVariable {
    	    public void naughty() {
    	        new InPackageB();
    	    }
    	}

    	assertTrue(matches(rule, UsesClassInPackageBAsAnonymousLocalVariable.class));
    }

    public void testUsesForInvocation() {
    	class UsesClassInPackageBForInvocation {
    	    public void naughty() {
    	        new InPackageB().empty();
    	    }
    	}

    	assertTrue(matches(rule, UsesClassInPackageBForInvocation.class));
    }

    public void testUsesForStaticInvocation() {
    	class UsesClassInPackageBForStaticInvocation {
    	    public void naughty() {
    	        InPackageB.staticEmpty();
    	    }
    	}

    	assertTrue(matches(rule, UsesClassInPackageBForStaticInvocation.class));
    }

    public void testUsesForClassInstanceField() {
    	class UsesClassInPackageBForClassInstanceField {
    	    Class naughtyClass = InPackageB.class;
    	}

    	assertTrue(matches(rule, UsesClassInPackageBForClassInstanceField.class));
    }
    
	static class UsesClassInStaticReturnValue {
		static InPackageB naughty() {
			return null;
		}
	}

	public void testUsesForStaticReturnValue() {
		assertTrue(matches(rule, UsesClassInStaticReturnValue.class));
    }

    public void testUsesForClassInstanceInMethod() {
    	class UsesClassInPackageBForClassInstanceInMethod {
    	    public void naughty() {
    	        System.out.println(InPackageB.class.getName());
    	    }
    	}

    	assertTrue(matches(rule, UsesClassInPackageBForClassInstanceInMethod.class));
    }

	public void testUsesForBaseClass() {
		class UsesClassForBaseClass extends InPackageB {
		}
		
		assertTrue(matches(rule, UsesClassForBaseClass.class));
	}

	public void testUsesForBaseInterface() {
		assertTrue(matches(rule, IUsesClassForBaseInterface.class));
	}

	public void testUsesForInterfaceImplementation() {
		class UsesClassForBaseInterface implements IInPackageB {
		}
		
		assertTrue(matches(rule, UsesClassForBaseInterface.class));
	}
	
	public void testUsesInMethodReturnInDeclaration() {
		class UsesClassInPackageBInMethodReturn {
			public InPackageB method() {
				return null;
			}
		}

		assertTrue(matches(rule, UsesClassInPackageBInMethodReturn.class));
	}
	
	public void testUsesClassInPackageBInMethodReturnOfInvokedMetho() {
		class UsesClassInPackageBInMethodReturn {
			public InPackageB method() {
				return null;
			}
		}

		class UsesClassInPackageBInMethodReturnOfInvokedMetho {
			public void method() {
				new UsesClassInPackageBInMethodReturn().method();
			}
		}

		assertTrue(matches(rule, UsesClassInPackageBInMethodReturnOfInvokedMetho.class));
	}

	public void testUsesInMethodParameters() {
		class UsesClassInPackageBInMethodParameters {
			public void method(InPackageB naughty) {
			}
		}

		assertTrue(matches(rule, UsesClassInPackageBInMethodParameters.class));
	}
	
	public void testUsesInConstructorParameters() {
		class UsesClassInPackageBInConstructorParameters {
			public UsesClassInPackageBInConstructorParameters(
				InPackageB naughty) {
			}
		}

		assertTrue(matches(rule, UsesClassInPackageBInConstructorParameters.class));
	}

	public void testUsesInMethodThrowsClause() {
		class UsesClassInPackageBInMethodThrowsClause {
			public void naughty() throws ExceptionInPackageB {
			}
		}

		assertTrue(matches(rule, UsesClassInPackageBInMethodThrowsClause.class));
	}

	public void testUsesInThrow() {
		class UsesClassInPackageBInThrow {
			public void naughty() {
				throw new ExceptionInPackageB();
			}
		}

		assertTrue(matches(rule, UsesClassInPackageBInThrow.class));
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
