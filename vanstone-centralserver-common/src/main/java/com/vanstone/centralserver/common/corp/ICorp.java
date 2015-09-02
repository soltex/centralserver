/**
 * 
 */
package com.vanstone.centralserver.common.corp;

/**
 * @author shipeng
 */
public interface ICorp {
	
	/**
	 * 获取App ID
	 * @return
	 */
	String getAppID();
	
	/**
	 * 获取App Secret
	 * @return
	 */
	String getAppSecret();
	
	/**
	 * 获取JSAPI的noncestr值
	 * @return
	 */
	String getJsAPINoncestr();
	
}
