package com.oreumio.james.rest;

/**
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String msg;
	private String type;

	public AppException() {
		super("Application exception");
	}
	
	public AppException(String msg) {
		super(msg);
	}
	
	public AppException(String msg, String type) {		
		super(msg);
		this.type = type;
	}

	public AppException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}	
}
