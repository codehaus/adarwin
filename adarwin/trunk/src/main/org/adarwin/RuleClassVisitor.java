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
import java.util.HashSet;
import java.util.Set;

import org.adarwin.rule.ElementType;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;

class RuleClassVisitor implements ClassVisitor {
    private CodeVisitor codeVisitor;
	private String fullyQualifiedClassName;
	private Set constructors;
	private Set methods;
	private Set dependancies;
	private TypeParser typeParser;

	public RuleClassVisitor() {
		constructors = new HashSet();
		methods = new HashSet();
		dependancies = new HashSet();
        codeVisitor = new RuleCodeVisitor();
        typeParser = new TypeParser();
    }

    public ClassSummary visit(ClassReader reader) {
		reader.accept(this, false);

        return new ClassSummary(fullyQualifiedClassName, constructors, methods, dependancies);
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

		IType type = typeParser.parse(desc);
		if (!type.isPrimative()) {
			inspect(new CodeElement(type, ElementType.USES));
		}
    }

	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions) {
		logln("visitMethod: " + name + ", " + desc + ", " + exceptions);
		
		if ("<init>".equals(name)) {
			IType[] parameterTypes = typeParser.parseMethodParameters(desc);
			String[] parameters = new String[parameterTypes.length];
			for (int pLoop = 0; pLoop < parameters.length; pLoop++) {
				parameters[pLoop] = parameterTypes[pLoop].getTypeName();
			}
			
			Constructor constructor = new Constructor(parameterTypes);
			constructors.add(constructor);
		}
		else {
			IType returnType = typeParser.parseMethodReturn(desc); 
			
			if (!returnType.isPrimative()) {
				inspect(new CodeElement(returnType.getTypeName(), ElementType.USES));
			}
			
			IType[] argumentTypes = typeParser.parseMethodParameters(desc);
			String[] parameterNames = new String[argumentTypes.length];
			for (int aLoop = 0; aLoop < argumentTypes.length; aLoop++) {
				parameterNames[aLoop] = argumentTypes[aLoop].getTypeName();
			}
			
			Method method = new Method(name, returnType.getTypeName(), parameterNames);
			methods.add(method);
		}

		if (exceptions != null) {
			for (int eLoop = 0; eLoop < exceptions.length; eLoop++) {
				String exceptionName = exceptions[eLoop].replace('/', '.');
				inspect(new CodeElement(exceptionName, ElementType.USES));
			}
		}

        return codeVisitor;
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
				IType type = typeParser.parse(desc);
				if (!type.isPrimative()) {
					inspect(new CodeElement(type.getTypeName(), ElementType.USES));
				}
			}
        }

        public void visitLineNumber(int i, Label label) {
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
