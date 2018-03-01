/**
 * 
 */
package com.example.marketplace.domain;

import com.example.marketplace.ErrorCode;

/**
 * @author srikanthgummula
 *
 * <p>Class for error code and messages</p>
 */
public class ErrorBean {

	private ErrorCode code;
	private String message;
	
	public ErrorBean(ErrorCode code, String msg) {
		this.code = code;
		this.message = msg;
	}
	/**
	 * @return the code
	 */
	public ErrorCode getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(ErrorCode code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
