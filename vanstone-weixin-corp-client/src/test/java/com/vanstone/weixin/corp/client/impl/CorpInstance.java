/**
 * 
 */
package com.vanstone.weixin.corp.client.impl;

import com.vanstone.weixin.corp.client.ICorp;

/**
 * @author shipeng
 *
 */
public enum CorpInstance implements ICorp {

	Sagacityidea("wx87c2c0c1d9ec9f7a", "DvNnPTVFkoQVNOh9VdjfcAnKOr_XaZCjwgxg8sNn4fHW1Co_2Thok2ymdnCjNLPq");

	private String appID;
	private String appSecret;

	private CorpInstance(String appID, String appSecret) {
		this.appID = appID;
		this.appSecret = appSecret;
	}

	@Override
	public String getAppID() {
		return this.appID;
	}

	@Override
	public String getAppSecret() {
		return this.appSecret;
	}

}
