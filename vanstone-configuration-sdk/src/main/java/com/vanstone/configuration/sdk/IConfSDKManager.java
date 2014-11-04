/**
 * 
 */
package com.vanstone.configuration.sdk;

/**
 * @author shipeng
 *
 */
public interface IConfSDKManager {

	/**
	 *  更新 Conf 值
	 * @param groupId
	 * @param dataId
	 * @param value
	 * @throws ConfNotFoundException
	 */
	void updateConf(String groupId, String dataId, String value) throws ConfNotFoundException;
	
	/**
	 * 更新 Conf 值 
	 * @param dataId
	 * @param value
	 * @throws ConfNotFoundException
	 */
	void updateConf(String dataId, String value) throws ConfNotFoundException;
	
	/**
	 * 本地添加Conf
	 * @param groupId
	 * @param dataId
	 * @param value
	 * @return
	 */
	boolean addConf(String groupId, String dataId, String value);
}
