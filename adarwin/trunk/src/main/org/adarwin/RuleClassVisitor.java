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

	public static ClassSummary visit(InputStream inputStream) throws ADarwinException {
		try {
			return new RuleClassVisitor().visit(new ClassReader(inputStream));
		}
		catch (IOException e) {
			throw new ADarwinException(e);
		}
		finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

    private ClassSummary visit(ClassReader reader) {
		reader.accept(this, false);

        return new ClassSummary(className, dependancies);
    }

	public void visit(int access, String sourceClassPath, String baseClassPath, String[] interfaces, String fileName) {
		className = getFullClassName(sourceClassPath);

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
    }

    public void visitField(int access, String name, String desc, Object value) {
		IType type = typeParser.parse(desc);
		if (!type.isPrimative()) {
			inspect(CodeElement.create(new ClassName(type.getTypeName()), ElementType.USES));
		}
    }

	public CodeVisitor visitMethod(int access, String methodName, String desc, String[] exceptions) {
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
		private Object lastLoaded;

        public void visitInsn(int opcode) {
        }

        public void visitIntInsn(int opcode, int operand) {
        }

        public void visitVarInsn(int opcode, int var) {
        }

        public void visitTypeInsn(int opcode, String desc) {
			inspect(CodeElement.create(getFullClassName(desc), ElementType.USES));
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (name.startsWith(STATIC_CLASS_PREFIX)) {
            	String text = name.substring(STATIC_CLASS_PREFIX.length());
            	
            	ClassName className = new ClassName(text.replace(STATIC_CLASS_SEPARATOR, '.'));
				inspect(CodeElement.create(className, ElementType.USES));
            }
        }

        public void visitMethodInsn(int opcode, String owner, String methodName, String desc) {
        	String returnType = typeParser.parseMethodReturn(desc).getTypeName();
        	String[] parameterTypes = convertToStringArray(typeParser.parseMethodParameters(desc));

        	ClassName fullClassName = getFullClassName(owner);
			if (isConstructor(methodName)) {
        		inspect(ConstructorInvocation.create(fullClassName, parameterTypes));
        	}
        	else {
        		if (Class.class.getName().equals(fullClassName.getFullClassName()) &&
        			"forName".equals(methodName)) {
        			
        			inspect(UsesCodeElement.create(new ClassName((String) lastLoaded))); 
        		}

        		inspect(MethodInvocation.create(fullClassName, returnType, methodName,
        			parameterTypes));
        	}
        }

		public void visitJumpInsn(int i, Label label) {
        }

        public void visitLabel(Label label) {
        }

        public void visitLdcInsn(Object object) {
        	lastLoaded = object;
        }

        public void visitIincInsn(int var, int increment) {
        }

        public void visitTableSwitchInsn(int min, int max, Label defaultLabel, Label[] labels) {
        }

        public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
        }

        public void visitMultiANewArrayInsn(String desc, int dims) {
        }

        public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        }

        public void visitMaxs(int maxStack, int maxLocals) {
        }

        public void visitLocalVariable(String name, String desc, Label start, Label end, int index) {
			if (!"this".equals(name)) {
				IType type = typeParser.parse(desc);
				if (!type.isPrimative()) {
					inspect(CodeElement.create(new ClassName(type.getTypeName()), ElementType.USES));
				}
			}
        }

        public void visitLineNumber(int line, Label start) {
        }
    }

	private ClassName getFullClassName(String packagePath) {
		return new ClassName(packagePath.replace('/', '.'));
	}
	
	public ClassSummary visit(Class clazz) throws IOException {
		return visit(new ClassReader(Util.getInputStream(clazz)));
	}
}
