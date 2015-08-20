/**
 * 
 */
package com.vanstone.weixin.corp.client;

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
	 * 回调响应的URL
	 * @return
	 */
	String getCallbackURL();
	
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
