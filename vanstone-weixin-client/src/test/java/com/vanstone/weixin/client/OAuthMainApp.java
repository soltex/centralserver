/**
 * 
 */
package com.vanstone.weixin.client;

import com.vanstone.centralserver.common.weixin.wrap.oauth2.Scope;

/**
 * @author shipeng
 *
 */
public class OAuthMainApp {
	
	public static void main(String[] args) {
		
		String appname = "jiujuyayuan";
		IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
		String text = weixinAPIManager.getOAuth2Url(appname, "http://weixin-service.c-cap.com.cn/jiujuyayuan/our-staffs.jhtml", Scope.snsapi_base, null);
		System.out.println(text);
	}
	
}
