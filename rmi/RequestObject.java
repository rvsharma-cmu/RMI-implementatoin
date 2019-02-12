package rmi;

import java.io.Serializable;

public class RequestObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2243771832033896888L;

	Exception exception;
	String mthdName;
	Object[] arguments;
	Class<?>[] argTypes;

	public RequestObject(String mthdName, Object[] arguments,Class<?>[] argTypes) {
		super();
		this.mthdName = mthdName;
		this.arguments = arguments;
		this.argTypes = argTypes; 
	}

	public Class<?>[] getArgTypes() {
		return argTypes;
	}

	public void setArgTypes(Class<?>[] argTypes) {
		this.argTypes = argTypes;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getMthdName() {
		return mthdName;
	}

	public void setMthdName(String mthdName) {
		this.mthdName = mthdName;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

}
