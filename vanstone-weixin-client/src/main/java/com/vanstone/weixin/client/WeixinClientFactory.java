/**
 * 
 */
package com.vanstone.weixin.client;

import com.vanstone.weixin.client.impl.WeixinAPIManagerImpl;

/**
 * Weixin Manager 工厂类
 * @author shipeng
 */
public class WeixinClientFactory {
	
	private static class WeixinApiManagerInstance {
		public static final IWeixinAPIManager instance = new WeixinAPIManagerImpl();
	}
	
	/**
	 * 获取WeixinAPIManager实例
	 * @return
	 */
	public static IWeixinAPIManager getWeixinAPIManager() {
		return WeixinApiManagerInstance.instance;
	}
	
}
