/**
 * 
 */
package com.vanstone.configuration.client;

import com.vanstone.configuration.client.impl.ConfigurationManagerImpl;

/**
 * @author shipeng
 *
 */
public class ConfigurationFactory {
	
	private static class ConfigurationManagerInstance {
		private static final IConfigurationManager instance = new ConfigurationManagerImpl();
		static {
			instance.start();
		}
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static IConfigurationManager getConfigurationManager() {
		return ConfigurationManagerInstance.instance;
	}
	
}
