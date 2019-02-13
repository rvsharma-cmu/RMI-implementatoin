package rmi;

import java.io.Serializable;

public class ResponseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6773255260019555732L;
	Object objName;
	Exception exception;

	public ResponseObject(Object objName, Exception exception) {
		super();
		this.objName = objName;
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Object getObjName() {
		return objName;
	}

	public void setObjName(Object objName) {
		this.objName = objName;
	}

}
