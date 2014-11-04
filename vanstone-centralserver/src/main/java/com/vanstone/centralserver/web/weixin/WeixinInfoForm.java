/**
 * 
 */
package com.vanstone.centralserver.web.weixin;

/**
 * @author shipeng
 */
public class WeixinInfoForm {
	private String id;
	private String appid;
	private String appSecret;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
