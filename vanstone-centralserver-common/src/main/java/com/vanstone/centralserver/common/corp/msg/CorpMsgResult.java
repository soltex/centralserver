/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;

/**
 * @author shipeng
 *
 */
public class CorpMsgResult {

	private ErrorCode errorCode;
	private String errMsg;
	private String invaliduser;
	private String invalidparty;
	private String invalidtag;

	public CorpMsgResult() {
		this.errorCode = ErrorCode.WEIXIN_SUCCESS_0;
	}
	
	public CorpMsgResult(ErrorCode errorCode, String errMsg, String invaliduser, String invalidparty, String invalidtag) {
		this.errorCode = errorCode;
		this.errMsg = errMsg;
		this.invalidparty = invalidparty;
		this.invalidtag = invalidtag;
		this.invaliduser = invaliduser;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public String getInvaliduser() {
		return invaliduser;
	}

	public String getInvalidparty() {
		return invalidparty;
	}

	public String getInvalidtag() {
		return invalidtag;
	}
	
}
