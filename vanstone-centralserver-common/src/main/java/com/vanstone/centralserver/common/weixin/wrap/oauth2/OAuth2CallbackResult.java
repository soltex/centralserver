/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.oauth2;

/**
 * OAuth2回调结果
 * @author shipeng
 */
public class OAuth2CallbackResult {

	private String code;
	private String state;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
