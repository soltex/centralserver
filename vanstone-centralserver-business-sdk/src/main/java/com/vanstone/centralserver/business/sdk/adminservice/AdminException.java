/**
 * 
 */
package com.vanstone.centralserver.business.sdk.adminservice;


/**
 * @author shipeng
 *
 */
public class AdminException extends Exception {

	/***/
	private static final long serialVersionUID = 6306017983890036794L;
	
	private ErrorCode errorCode;
	
	public AdminException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * 创建AdminException
	 * @param errorCode
	 * @return
	 */
	public static AdminException create(ErrorCode errorCode) {
		return new AdminException(errorCode);
	}
	
	public static enum ErrorCode {
		AdminName_Not_Found,
		AdminName_Password_Not_Match,
		Admin_Hasbeen_Forbit;
	}
}
