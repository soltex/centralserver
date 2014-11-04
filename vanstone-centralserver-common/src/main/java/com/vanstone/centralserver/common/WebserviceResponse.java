/**
 * 
 */
package com.vanstone.centralserver.common;


/**
 * Webservice Response
 * @author shipeng
 */
public class WebserviceResponse {

	private ResponseCode responseCode;
	private String message;
	
	/**
	 * 创建Response对象
	 * @param responseCode
	 * @param message
	 * @return
	 */
	public static WebserviceResponse createResponse(ResponseCode responseCode, String message) {
		MyAssert.notNull(responseCode);
		WebserviceResponse response = new WebserviceResponse();
		response.setResponseCode(responseCode);
		response.setMessage(message);
		return response;
	}
	
	/**
	 * 创建Response对象
	 * @param responseCode
	 * @return
	 */
	public static WebserviceResponse createResponse(ResponseCode responseCode) {
		return createResponse(responseCode, null);
	}
	
	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static enum ResponseCode {
		Success("成功", 1), 
		Illegal_Argument_Error("非法参数错误",-1),
		GroupId_DataId_Not_Found("GroupId DataId not found", -11),
		GroupId_DataId_Duplicate("Group ID Data ID Duplicate", 12);
		
		private String desc;
		private Integer code;

		private ResponseCode(String desc, Integer code) {
			this.desc = desc;
			this.code = code;
		}

		public Integer getCode() {
			return code;
		}
		
		public String getDesc() {
			return desc;
		}

	}

}
