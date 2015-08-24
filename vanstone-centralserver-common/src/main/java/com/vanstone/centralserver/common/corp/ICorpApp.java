/**
 * 
 */
package com.vanstone.centralserver.common.corp;

/**
 * @author shipeng
 * 
 */
public interface ICorpApp {

	/**
	 * 企业应用ID
	 * @return
	 */
	int getId();

	/**
	 * 获取企业应用的描述（自定义值，和微信上定的名称无关）
	 * @return
	 */
	String getDesc();
	
	/**
	 * 当前应用监听器
	 * @return
	 */
	PassiveMsgListener getListener();
	
	/**
	 * 获取当前应用的令牌Token值
	 * @return
	 */
	String getToken();
	
	/**
	 * 获取当前应用的Encoding AES Key 值
	 * @return
	 */
	String getEncodingAESKey();
	
}
