package org.ajester;

import org.objectweb.asm.Constants;

public class Access {
	public final boolean isAbstract;
	public final boolean isFinal;
	public final boolean isNative;
	public final boolean isPrivate;
	public final boolean isProtected;
	public final boolean isPublic;
	public final boolean isStatic;
	public final boolean isStrict;
	public final boolean isSynchronized;
	public final boolean isTransient;
	public final boolean isVolatile;

	public Access(int access) {
		isPublic = (access & Constants.ACC_PUBLIC) != 0;
		isPrivate = (access & Constants.ACC_PRIVATE) != 0;
		isProtected = (access & Constants.ACC_PROTECTED) != 0;
		isFinal = (access & Constants.ACC_FINAL) != 0;
		isStatic = (access & Constants.ACC_STATIC) != 0;
		isSynchronized = (access & Constants.ACC_SYNCHRONIZED) != 0;
		isVolatile = (access & Constants.ACC_VOLATILE) != 0;
		isTransient = (access & Constants.ACC_TRANSIENT) != 0;
		isNative = (access & Constants.ACC_NATIVE) != 0;
		isAbstract = (access & Constants.ACC_ABSTRACT) != 0;
		isStrict = (access & Constants.ACC_STRICT) != 0;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		if (isPublic) {
			buffer.append("public ");
		}
		
		if (isPrivate) {
			buffer.append("private ");
		}
		
		if (isProtected) {
			buffer.append("protected ");
		}
		
		if (isFinal) {
			buffer.append("final ");
		}
		
		if (isStatic) {
			buffer.append("static ");
		}
		
		if (isSynchronized) {
			buffer.append("synchronized ");
		}
		
		if (isVolatile) {
			buffer.append("volatile ");
		}
		
		if (isTransient) {
			buffer.append("transient ");
		}
		
		if (isNative) {
			buffer.append("native ");
		}
		
		if (isAbstract) {
			buffer.append("abstract ");
		}
		
		if (isStrict) {
			buffer.append("strictfp ");
		}
		
		return buffer.toString();	
	}
}
