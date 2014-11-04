/**
 * 
 */
package com.vanstone.centralserver.business.sdk.configuration;

import java.util.Collection;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.util.web.PageInfo;

/**
 * @author shipeng
 */
public interface IConfigurationServiceMgr {
	
	public static final String SERVICE = "configurationService";
	
	/**
	 * 获取值
	 * @param dataid
	 * @param groupid
	 * @return
	 */
	String getValue(String groupid,String dataid);
	
	/**
	 * 获取Integer值
	 * @param groupid
	 * @param dataid
	 * @return
	 */
	Integer getIntValue(String groupid,String dataid);
	
	/**
	 * 获取double值
	 * @param groupid
	 * @param dataid
	 * @return
	 */
	Double getDoubleValue(String groupId,String dataId);
	
	/**
	 * 获取Float值
	 * @param groupid
	 * @param dataid
	 * @return
	 */
	Float getFloatValue(String groupId,String dataId);
	
	/**
	 * 添加Conf
	 * @param groupId
	 * @param dataId
	 * @param value
	 * @throws ObjectDuplicateException
	 */
	void addConf(String groupId,String dataId,String value) throws ObjectDuplicateException;
	
	/**
	 * 更新Conf
	 * @param groupId
	 * @param dataId
	 * @param value
	 */
	void updateConf(int id, String groupId, String dataId, String value) throws ObjectDuplicateException ;
	
	/**
	 * 通过GroupId DataId 更新 Value
	 * @param groupId
	 * @param dataId
	 * @param value
	 */
	void updateConf(String groupId, String dataId, String value);
	
	/**
	 * 删除Conf
	 * @param groupId
	 * @param dataId
	 */
	void deleteConf(String groupId,String dataId);
	
	/**
	 * 通过GroupId删除Conf
	 * @param groupId
	 */
	void deleteConfsByGroupId(String groupId);
	
	/**
	 * 获取Groups
	 * @return
	 */
	Collection<String> getGroups();
	
	/**
	 * 通过GroupId获取Collection<IConfInfo>
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    Collection getConfsByGroupId(String groupId);
	
	/**
	 * 检索Confs获取Collection<IConfInfo>
	 * @param groupId
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    Collection getConfs(String key, int offset, int limit);
	
	/**
	 * 获取Conf数量
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	int getTotalConfs(String key);
	
	/**
	 * 检索
	 * @param key
	 * @param p
	 * @return
	 */
	PageInfo<IConfInfo> getConfInfosPageInfo(String key, int p, int pagesize);
	
	/**
	 * 刷新全部Confs
	 */
	void refreshAllConfs();
	
	/**
	 * 刷新Conf
	 * @param groupId
	 * @param dataId
	 */
	void refreshConf(String groupId, String dataId);
	
	/**
	 * 关闭
	 */
	void close();
	
}	
