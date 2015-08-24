/**
 * 
 */
package com.vanstone.centralserver.common.corp.oauth2;

/**
 * @author shipeng
 *
 */
public class RedirectResult {

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
