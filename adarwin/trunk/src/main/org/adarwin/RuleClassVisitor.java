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

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adarwin.rule.ElementType;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;

class RuleClassVisitor implements ClassVisitor {
    private CodeVisitor codeVisitor;
	private String fullyQualifiedClassName;
	private Set dependancies;
	private Set constructors;

	public RuleClassVisitor() {
		constructors = new HashSet();
		dependancies = new HashSet();
        codeVisitor = new RuleCodeVisitor();
    }

    public ClassSummary visit(ClassReader reader) {
		reader.accept(this, false);

        return new ClassSummary(fullyQualifiedClassName, constructors, Collections.EMPTY_SET,
        	dependancies);
    }

	public void visit(int access, String sourceClassPath, String baseClassPath, String[] interfaces, String fileName) {
		fullyQualifiedClassName = getFullyQualifiedClassName(sourceClassPath);

		inspect(new CodeElement(getFullyQualifiedClassName(sourceClassPath), ElementType.SOURCE));

		String fullyQualifiedClassName = getFullyQualifiedClassName(baseClassPath);

		inspect(new CodeElement(fullyQualifiedClassName, ElementType.EXTENDS_OR_IMPLEMENTS));

		for (int iLoop = 0; iLoop < interfaces.length; ++iLoop) {
			fullyQualifiedClassName = getFullyQualifiedClassName(interfaces[iLoop]);
			inspect(new CodeElement(fullyQualifiedClassName, ElementType.EXTENDS_OR_IMPLEMENTS));
		}
    }

	private void inspect(CodeElement codeElement) {
		if (!Object.class.getName().equals(codeElement.getFullyQualifiedClassName())) {
			dependancies.add(codeElement);
		}
	}

	public void visitInnerClass(String string, String string1, String string2, int i) {
		logln("visitInnerClass: " + string + ", " + string1 + ", " + string2);
    }

    public void visitField(int access, String name, String desc, Object value) {
		log("visitField: ");
		log("name = " + name);
		log(", desc = " + desc);
		logln(", value = " + value);

        String fullyQualifiedClassName = getFullyQualifiedClassName(stripOddStuff(desc));
		inspect(new CodeElement(fullyQualifiedClassName, ElementType.USES));
    }

	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions) {
		logln("visitMethod: " + name + ", " + desc + ", " + exceptions);
		
		if ("<init>".equals(name)) {
			
			Type[] parameterTypes = Type.getArgumentTypes(desc);
			String[] parameters = new String[parameterTypes.length];
			for (int pLoop = 0; pLoop < parameters.length; pLoop++) {
				if (!isPrimative(parameterTypes[pLoop])) {
					parameters[pLoop] = parameterTypes[pLoop].getClassName();
				}
				else {
					parameters[pLoop] = parameterTypes[pLoop].getDescriptor();
				}
			}
			
			Constructor constructor = new Constructor(parameters);
			constructors.add(constructor);
		}
		else {
			Type returnType = Type.getReturnType(desc);
			if (desc.indexOf(")L") != -1) {
				inspect(new CodeElement(returnType.getClassName(), ElementType.USES));
			}
		}

		if (exceptions != null) {
			for (int eLoop = 0; eLoop < exceptions.length; eLoop++) {
				String exceptionName = exceptions[eLoop].replace('/', '.');
				inspect(new CodeElement(exceptionName, ElementType.USES));
			}
		}

        return codeVisitor;
    }

	private boolean isPrimative(Type type) {
		return Type.BOOLEAN_TYPE == type ||
			Type.BYTE_TYPE == type ||
			Type.CHAR_TYPE == type ||
			Type.DOUBLE_TYPE == type ||
			Type.FLOAT_TYPE == type ||
			Type.INT_TYPE == type ||
			Type.LONG_TYPE == type ||
			Type.SHORT_TYPE == type;
	}

	public void visitEnd() {
	}

	public class RuleCodeVisitor implements CodeVisitor {
        public static final char STATIC_CLASS_SEPARATOR = '$';
        public static final String STATIC_CLASS_PREFIX = "class" + STATIC_CLASS_SEPARATOR;

        public void visitInsn(int i) {
        }

        public void visitIntInsn(int i, int i1) {
        }

        public void visitVarInsn(int i, int i1) {
        }

        public void visitTypeInsn(int opcode, String desc) {
        	logln("visitTypeInsn: desc = " + desc);
            String fullyQualifiedClassName = getFullyQualifiedClassName(desc);
			inspect(new CodeElement(fullyQualifiedClassName, ElementType.USES));
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        	log("visitFieldInsn: ");
        	log("owner = " + owner);
        	log(", name = " + name);
        	logln(", desc = " + desc);

            if (name.startsWith(STATIC_CLASS_PREFIX)) {
                String fullyQualifiedClassName = name.substring(STATIC_CLASS_PREFIX.length())
                    .replace(STATIC_CLASS_SEPARATOR, '.');

				inspect(new CodeElement(fullyQualifiedClassName, ElementType.USES));
            }
        }

        public void visitMethodInsn(int opcaode, String owner, String name, String desc) {
        	log("visitMethodInsn: ");
        	log("owner = " + owner);
        	log(", name = " + name);
        	logln(", desc = " + desc);
        	
            String fullyQualifiedClassName = getFullyQualifiedClassName(owner);
			inspect(new CodeElement(fullyQualifiedClassName, ElementType.USES));
        }

		public void visitJumpInsn(int i, Label label) {
            logln("visitJumpInsn: " + label);
        }

        public void visitLabel(Label label) {
        	logln("visitLabel: " + label);
        }

        public void visitLdcInsn(Object object) {
            logln("visitLdcInsn:  " + object);
        }

        public void visitIincInsn(int i, int i1) {
        }

        public void visitTableSwitchInsn(int i, int i1, Label label, Label[] labels) {
        }

        public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
        }

        public void visitMultiANewArrayInsn(String string, int i) {
        	logln("visitMultiANewArrayInsn: " + string);
        }

        public void visitTryCatchBlock(Label label, Label label1, Label label2, String string) {
        }

        public void visitMaxs(int i, int i1) {
        }

        public void visitLocalVariable(String name, String desc, Label start, Label end, int index) {
			if (!"this".equals(name)) {
				String fullyQualifiedClassName = getFullyQualifiedClassName(stripOddStuff(desc));
				inspect(new CodeElement(fullyQualifiedClassName, ElementType.USES));
			}
        }

        public void visitLineNumber(int i, Label label) {
        }
    }

	private String stripOddStuff(String desc) {
		if (desc.endsWith(";") && desc.startsWith("L")) {
			return desc.substring(1, desc.length() - 1);
		}
		else {
			return desc;
		}
	}

	private String getFullyQualifiedClassName(String packagePath) {
		return packagePath.replace('/', '.');
	}

	public ClassSummary visit(ClassFile classFile) throws IOException {
		return classFile.accept(this);
	}


	private void log(String toLog) {
//		System.out.print(toLog);
	}
	
	private void logln(String toLog) {
//		System.out.println(toLog);
	}
}
