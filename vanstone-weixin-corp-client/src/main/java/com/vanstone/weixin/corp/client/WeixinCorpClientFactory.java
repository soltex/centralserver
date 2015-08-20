/**
 * 
 */
package com.vanstone.weixin.corp.client;

import com.vanstone.weixin.corp.client.impl.WeixinCorpClientManagerImpl;

/**
 * @author shipeng
 *
 */
public class WeixinCorpClientFactory {
	
	private static WeixinCorpClientManager weixinCorpClientManager = new WeixinCorpClientManagerImpl();
	
	/**
	 * 获取微信企业号管理器实例
	 * @return
	 */
	public static WeixinCorpClientManager getWeixinCorpClientManager() {
		return weixinCorpClientManager;
	}
	
}
