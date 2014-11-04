/**
 * 
 */
package com.vanstone.centralserver.common.weixin;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.gson.Gson;
import com.vanstone.centralserver.common.MyAssert;

/**
 * App开发者信息
 * @author shipeng
 */
public class AppDevInfo {
	
	/** 应用名称 */
	private String appname;
	/** 开发这Appid*/
	private String appid;
	/** 开发者密钥*/
	private String secret;
	/** 开发者accessToken*/
	private String accessToken;
	
	public String getAppname() {
		return appname;
	}
	
	public void setAppname(String appname) {
		this.appname = appname;
	}
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	/**
	 * 转换为Json字符串
	 * @return
	 */
	public String toJsonString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	/**
	 * 通过Json字符串获取AppDevInfo对象
	 * @param json
	 * @return
	 */
	public static AppDevInfo parseAppDevInfoByJson(String json) {
		MyAssert.hasText(json);
		Gson gson = new Gson();
		return gson.fromJson(json, AppDevInfo.class);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
