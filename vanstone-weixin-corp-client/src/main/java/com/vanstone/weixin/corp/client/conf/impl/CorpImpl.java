/**
 * 
 */
package com.vanstone.weixin.corp.client.conf.impl;

import com.vanstone.centralserver.common.corp.ICorp;

/**
 * @author shipeng
 *
 */
public class CorpImpl implements ICorp {

	private String AppID;
	private String appSecret;
	private String jsAPINoncestr;
	
	@Override
	public String getAppID() {
		return this.AppID;
	}

	@Override
	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppID(String appID) {
		AppID = appID;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getJsAPINoncestr() {
		return jsAPINoncestr;
	}
	
	public void setJsAPINoncestr(String jsAPINoncestr) {
		this.jsAPINoncestr = jsAPINoncestr;
	}
	
}
