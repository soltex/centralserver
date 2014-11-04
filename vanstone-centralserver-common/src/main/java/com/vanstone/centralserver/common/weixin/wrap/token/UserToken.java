/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.token;

/**
 * User Token 对象
 * @author shipeng
 */
public class UserToken {

	/** APPID */
	private String appId;
	/** APP SECRET */
	private String appSecret;
	/** access Token */
	private String accessToken;
	/** 失效秒数 */
	private int expireTimeInSecond;
	
	public UserToken(String appId, String appSecret, String accessToken, int expireTimeInSecond) {
		if (appId == null || "".equals(appId) || appSecret == null || "".equals(appSecret) || accessToken == null
		        || "".equals(accessToken) || expireTimeInSecond <= 0) {
			throw new IllegalArgumentException();
		}
		this.appId = appId;
		this.appSecret = appSecret;
		this.accessToken = accessToken;
		this.expireTimeInSecond = expireTimeInSecond;
	}
	
	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}
	
	public int getExpireTimeInSecond() {
		return expireTimeInSecond;
	}

}
