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
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.adarwin.rule.ElementType;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;

class RuleClassVisitor implements ClassVisitor {
    private final CodeVisitor codeVisitor = new RuleCodeVisitor();
	private final TypeParser typeParser = new TypeParser();
	private final Set dependancies = new HashSet();
	private ClassName className;

	public static ClassSummary visit(InputStream inputStream) throws IOException {
		try {
			return new RuleClassVisitor().visit(new ClassReader(inputStream));
		}
		finally {
			inputStream.close();
		}
	}

    public ClassSummary visit(ClassReader reader) {
		reader.accept(this, false);

        return new ClassSummary(className, dependancies);
    }

	public void visit(int access, String sourceClassPath, String baseClassPath, String[] interfaces, String fileName) {
		log("visit: ");
		log("access: " + access);
		log(", sourceClassPath: " + sourceClassPath);
		log(", baseClassPath: " + baseClassPath);
		if (interfaces != null) {
			for (int iLoop = 0; iLoop < interfaces.length; ++iLoop) {
				log(", interfaces[" + iLoop + "] = " + interfaces[iLoop]);
			}
		}
		logln(", filename: " + fileName);

		className = getFullClassName(sourceClassPath);

		inspect(CodeElement.create(className, ElementType.SOURCE));

		inspect(CodeElement.create(getFullClassName(baseClassPath),
			ElementType.EXTENDS_OR_IMPLEMENTS));

		for (int iLoop = 0; iLoop < interfaces.length; ++iLoop) {
			inspect(CodeElement.create(getFullClassName(interfaces[iLoop]),
				ElementType.EXTENDS_OR_IMPLEMENTS));
		}
    }

	private void inspect(CodeElement codeElement) {
		if (!isObject(codeElement)) {
			dependancies.add(codeElement);
		}
	}

	private boolean isObject(CodeElement codeElement) {
		return codeElement.getClassName().isObject();
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		logln("visitInnerClass: " + name + ", " + outerName + ", " + innerName);
    }

    public void visitField(int access, String name, String desc, Object value) {
		log("visitField: ");
		log("name = " + name);
		log(", desc = " + desc);
		logln(", value = " + value);

		IType type = typeParser.parse(desc);
		if (!type.isPrimative()) {
			inspect(CodeElement.create(new ClassName(type.getTypeName()), ElementType.USES));
		}
    }

	public CodeVisitor visitMethod(int access, String methodName, String desc, String[] exceptions) {
		logln("visitMethod: " + methodName + ", " + desc + ", " + exceptions);
		
		if (isConstructor(methodName)) {
			dependancies.add(new ConstructorDeclaration(className,
				convertToStringArray(typeParser.parseMethodParameters(desc))));
		}
		else {
			IType returnType = typeParser.parseMethodReturn(desc); 
			
			if (!returnType.isPrimative()) {
				inspect(CodeElement.create(new ClassName(returnType.getTypeName()), ElementType.USES));
			}
			
			IType[] argumentTypes = typeParser.parseMethodParameters(desc);
			String[] parameterNames = new String[argumentTypes.length];
			for (int aLoop = 0; aLoop < argumentTypes.length; aLoop++) {
				parameterNames[aLoop] = argumentTypes[aLoop].getTypeName();
			}

			dependancies.add(new MethodDeclaration(className, methodName, returnType.getTypeName(), parameterNames));
		}

		if (exceptions != null) {
			for (int eLoop = 0; eLoop < exceptions.length; eLoop++) {
				inspect(CodeElement.create(getFullClassName(exceptions[eLoop]), ElementType.USES));
			}
		}

        return codeVisitor;
    }

	private boolean isConstructor(String name) {
		return "<init>".equals(name);
	}

	private String[] convertToStringArray(IType[] parameterTypes) {
		String[] parameters;
		parameters = new String[parameterTypes.length];
		for (int pLoop = 0; pLoop < parameters.length; pLoop++) {
			parameters[pLoop] = parameterTypes[pLoop].getTypeName();
		}
		return parameters;
	}

	public void visitEnd() {
	}

	public class RuleCodeVisitor implements CodeVisitor {
        public static final char STATIC_CLASS_SEPARATOR = '$';
        public static final String STATIC_CLASS_PREFIX = "class" + STATIC_CLASS_SEPARATOR;

        public void visitInsn(int opcode) {
        	logln("visitInsn: opcode = " + opcode);
        }

        public void visitIntInsn(int opcode, int operand) {
        	log("visitIntInsn: opcode = " + opcode);
        	logln(", operand = " + operand);
        }

        public void visitVarInsn(int opcode, int var) {
        	log("visitVarInsn: opcode = " + opcode);
        	logln(", var = " + var);
        }

        public void visitTypeInsn(int opcode, String desc) {
        	log("visitTypeInsn: opcode = " + opcode);
        	logln(", desc = " + desc);
			inspect(CodeElement.create(getFullClassName(desc), ElementType.USES));
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        	log("visitFieldInsn: ");
        	log("owner = " + owner);
        	log(", name = " + name);
        	logln(", desc = " + desc);

            if (name.startsWith(STATIC_CLASS_PREFIX)) {
            	ClassName className = new ClassName(name.substring(STATIC_CLASS_PREFIX.length())
                    .replace(STATIC_CLASS_SEPARATOR, '.'));

				inspect(CodeElement.create(className, ElementType.USES));
            }
        }

        public void visitMethodInsn(int opcaode, String owner, String methodName, String desc) {
        	log("visitMethodInsn: ");
        	log("owner = " + owner);
        	log(", name = " + methodName);
        	logln(", desc = " + desc);

        	String returnType = typeParser.parseMethodReturn(desc).getTypeName();
        	String[] parameterTypes = convertToStringArray(typeParser.parseMethodParameters(desc));

        	if (isConstructor(methodName)) {
        		inspect(ConstructorInvocation.create(getFullClassName(owner), parameterTypes));
        	}
        	else {
        		inspect(MethodInvocation.create(getFullClassName(owner), returnType, methodName,
        			parameterTypes));
        	}
        }

		public void visitJumpInsn(int i, Label label) {
        }

        public void visitLabel(Label label) {
        }

        public void visitLdcInsn(Object object) {
            logln("visitLdcInsn:  " + object);
        }

        public void visitIincInsn(int var, int increment) {
        	log("visitIincInsn: var = " + var);
        	logln(", increment = " + increment);
        }

        public void visitTableSwitchInsn(int min, int max, Label defaultLabel, Label[] labels) {
        	log("visitTableSwitchInsn: min = " + min);			logln(", max = " + max);        }

        public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
        }

        public void visitMultiANewArrayInsn(String desc, int dims) {
        	log("visitMultiANewArrayInsn: desc = " + desc);
        	logln(", dims = " + dims);
        }

        public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        	logln("visitTryCatchBlock: type = " + type);
        }

        public void visitMaxs(int maxStack, int maxLocals) {
        	log("visitMaxs: maxStack = " + maxStack);
        	logln(", maxLocals = " + maxLocals);
        }

        public void visitLocalVariable(String name, String desc, Label start, Label end, int index) {
        	log("visitLocalVariable: name = " + name);
        	log(", desc = " + desc);
        	logln(", index = " + index);
        	
			if (!"this".equals(name)) {
				IType type = typeParser.parse(desc);
				if (!type.isPrimative()) {
					inspect(CodeElement.create(new ClassName(type.getTypeName()), ElementType.USES));
				}
			}
        }

        public void visitLineNumber(int line, Label start) {
        	logln("visitLineNumber: line = " + line);
        }
    }

	private ClassName getFullClassName(String packagePath) {
		return new ClassName(packagePath.replace('/', '.'));
	}
	
	public ClassSummary visit(Class clazz) throws IOException {
		return visit(new ClassReader(Util.getInputStream(clazz)));
	}

	private void log(String toLog) {
//		System.out.print(toLog);
	}
	
	private void logln(String toLog) {
//		System.out.println(toLog);
	}
}
