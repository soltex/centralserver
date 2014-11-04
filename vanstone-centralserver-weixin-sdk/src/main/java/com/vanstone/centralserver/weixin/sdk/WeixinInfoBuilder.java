/**
 * 
 */
package com.vanstone.centralserver.weixin.sdk;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.centralserver.business.sdk.weixin.IWeixinInfo;
import com.vanstone.centralserver.weixin.sdk.persistence.object.WeixinInfoDOWithBLOBs;

/**
 * 微信信息创建器
 * @author shipengpipi@126.com
 */
public class WeixinInfoBuilder {
	
	/**
	 * 创建微信配置信息
	 * @param id
	 * @param appid
	 * @param appSecret
	 * @param content
	 * @return
	 */
	public static IWeixinInfo create(String id, String appid, String appSecret, String content) {
		MyAssert4Business.hasText(id);
		MyAssert4Business.hasText(appid);
		MyAssert4Business.hasText(appSecret);
		
		WeixinInfoDOWithBLOBs model = new WeixinInfoDOWithBLOBs();
		model.setId(id);
		model.setAppid(appid);
		model.setAppSecret(appSecret);
		model.setContent(content);
		return model;
	}
	
}
