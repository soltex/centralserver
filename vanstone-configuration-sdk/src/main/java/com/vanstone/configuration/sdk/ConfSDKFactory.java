/**
 * 
 */
package com.vanstone.configuration.sdk;

import com.vanstone.configuration.sdk.impl.ConfSDKManagerImpl;

/**
 * @author shipeng
 */
public class ConfSDKFactory {
	
	public static final class ConfigurationSDKInstance {
		private static final IConfSDKManager instance = new ConfSDKManagerImpl();
	}
	
	/**
	 * 获取SDK管理单例
	 * @return
	 */
	public static IConfSDKManager getConfSDKManager() {
		return ConfigurationSDKInstance.instance;
	}
	
}
