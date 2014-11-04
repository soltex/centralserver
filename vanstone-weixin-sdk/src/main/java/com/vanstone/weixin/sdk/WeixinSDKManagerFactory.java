/**
 * 
 */
package com.vanstone.weixin.sdk;

import com.vanstone.weixin.sdk.impl.WeixinSDKManagerImpl;

/**
 * @author shipeng
 */
public class WeixinSDKManagerFactory {
	
	private static class WeixinSDKManagerInstance {
		private static final IWeixinSDKManager instance = new WeixinSDKManagerImpl();
	}
	
	/**
	 * 获取微信SDK管理类实例
	 * @return
	 */
	public static IWeixinSDKManager getWeixinSDKManager() {
		return WeixinSDKManagerInstance.instance;
	}
}
