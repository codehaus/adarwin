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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;

class RuleClassVisitor implements ClassVisitor {
    private final CodeVisitor codeVisitor = new RuleCodeVisitor();
	private final TypeParser typeParser = new TypeParser();
	private final Set dependancies = new HashSet();
	private String className;

	public static ClassSummary visit(InputStream inputStream) {
		try {
			return new RuleClassVisitor().visit(new ClassReader(inputStream));
		}
		catch (IOException e) {
			throw new ADarwinException(e);
		}
		finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			}
			catch (IOException e) {
			}
		}
	}

    private ClassSummary visit(ClassReader reader) {
		reader.accept(this, false);

		return new ClassSummary(className, dependancies);
    }

	public void visit(int access, String sourceClassPath, String baseClassPath, String[] interfaces,
		String fileName) {

		className = getFullClassName(sourceClassPath); 

		inspect(CodeElement.createExtends(getFullClassName(baseClassPath)));

		for (int iLoop = 0; iLoop < interfaces.length; ++iLoop) {
			inspect(CodeElement.createExtends(getFullClassName(interfaces[iLoop])));
		}
    }

	private void inspect(CodeElement codeElement) {
		if (!codeElement.involvesObject()) {
			dependancies.add(codeElement);
		}
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }

    public void visitField(int access, String name, String desc, Object value) {
		inspect(CodeElement.createUses(typeParser.typeName(desc)));
    }

	public CodeVisitor visitMethod(int access, String methodName, String desc, String[] exceptions) {
		String[] parameterNames = typeParser.parameterTypes(desc);

		if (isConstructor(methodName)) {
			dependancies.add(new ConstructorDeclaration(className, parameterNames));
		}
		else {
			inspect(CodeElement.createUses(typeParser.returnType(desc)));

			dependancies.add(new MethodDeclaration(className, methodName,
				typeParser.returnType(desc), parameterNames));
		}

		if (exceptions != null) {
			for (int eLoop = 0; eLoop < exceptions.length; eLoop++) {
				inspect(CodeElement.createUses(getFullClassName(exceptions[eLoop])));
			}
		}

        return codeVisitor;
    }

	private boolean isConstructor(String name) {
		return "<init>".equals(name);
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
			inspect(CodeElement.createUses(getFullClassName(desc)));
        }

        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (name.startsWith(STATIC_CLASS_PREFIX)) {
            	String text = name.substring(STATIC_CLASS_PREFIX.length());
            	
				inspect(CodeElement.createUses(text.replace(STATIC_CLASS_SEPARATOR, '.')));
            }
        }

        public void visitMethodInsn(int opcode, String owner, String methodName, String desc) {
        	String className = getFullClassName(owner);
        	String[] parameterTypes = typeParser.parameterTypes(desc);

			if (isConstructor(methodName)) {
        		inspect(ConstructorInvocation.create(className, parameterTypes));
        	}
        	else {
        		if (Class.class.getName().equals(className) && "forName".equals(methodName)) {
        			inspect(CodeElement.createUses((String) lastLoaded)); 
        		}

            	inspect(CodeElement.createUses(typeParser.returnType(desc)));

        		inspect(MethodInvocation.create(className, typeParser.returnType(desc), methodName,
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
				inspect(CodeElement.createUses(typeParser.typeName(desc)));
			}
        }

        public void visitLineNumber(int line, Label start) {
        }
    }

	private String getFullClassName(String packagePath) {
		return packagePath.replace('/', '.');
	}
}
