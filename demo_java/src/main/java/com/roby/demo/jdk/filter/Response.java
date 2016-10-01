package com.roby.demo.jdk.filter;
/**
 * 出参
 * @author jlf
 *
 */
public class Response {
	 private String head;
	 private String object;
	 private Throwable exception; //error when the error has been declare in the interface
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public Throwable getException() {
		return exception;
	}

	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
	


}
