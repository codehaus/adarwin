package picounit.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ScopeImpl implements Scope {
	private final Class type;
	private final String typeName;
	private final Object value;
	private Throwable reason;

	public ScopeImpl(String typeName, Class type, Object value) {
		this.typeName = typeName;
		this.type = type;
		this.value = value;
	}

	public boolean matches(String typeNameFilter) {
		return typeName.equals(typeNameFilter);
	}
	
	public boolean matches(Class filter) {
		return type.equals(filter);
	}

	public Object value() {
		return value;
	}
	
	public String toString() {
		StringWriter stringWriter = new StringWriter();

		if (reason != null) {
			reason.printStackTrace(new PrintWriter(stringWriter));
		}

		return type + ": " + value + "\n" + stringWriter.toString();
	}

	public Class getType() {
		return type;
	}
	
	public boolean equals(Object object) {
		if (object == null || !object.getClass().equals(getClass())) {
			return false;
		}
		
		if (object == this) {
			return true;
		}
		
		ScopeImpl other = (ScopeImpl) object;

		return type.equals(other.type) && value.equals(other.value);
	}

	public void setFailure(Throwable reason) {
		this.reason = reason;
	}
	
	public Throwable getFailure() {
		return reason;
	}
}