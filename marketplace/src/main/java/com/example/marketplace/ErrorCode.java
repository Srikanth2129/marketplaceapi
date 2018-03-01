/**
 * 
 */
package com.example.marketplace;

/**
 * @author srikanthgummula
 *
 * <p>This class provides all Error codes</p>
 */
public enum ErrorCode {
	
	INVALID_REQUEST("INVALID_REQUEST"),
	RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
	SERVER_ERROR("RESOURCE_NOT_FOUND");
	
	private String code;
	
	ErrorCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return code;
	}
	
	/**
	 * Returns ErrorCode from string value.
	 * @param str
	 * @return
	 */
	public static ErrorCode fromValue(String str) {
		for(ErrorCode errorCode: ErrorCode.values()) {
			if(errorCode.toString().equalsIgnoreCase(str)) {
				return errorCode;
			}
		}
		return null;
	}

}
