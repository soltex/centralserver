/**
 * 
 */
package com.vanstone.centralserver.business.sdk.configuration;

import java.util.Date;

/**
 * @author shipeng
 */
public interface IConfInfo {
	
	/**
	 * 获取Id
	 * @return
	 */
	Integer getId();

	/**
	 * 获取DataId
	 * @return
	 */
	String getDataId();

	/**
	 * 设定DataId
	 * @param dataId
	 */
	void setDataId(String dataId);

	/**
	 * 获取分组ID
	 * @return
	 */
	String getGroupId();

	/**
	 * 设定GroupId
	 * @param groupId
	 */
	void setGroupId(String groupId);

	/**
	 * 获取写入时间
	 * @return
	 */
	Date getSysInsertTime();

	/**
	 * 获取更新时间
	 * @return
	 */
	Date getSysUpdateTime();

	/**
	 * 获取配置信息
	 * @return
	 */
	String getConfValue();

	/**
	 * 设定配置信息
	 * @param confValue
	 */
	void setConfValue(String confValue);
}
