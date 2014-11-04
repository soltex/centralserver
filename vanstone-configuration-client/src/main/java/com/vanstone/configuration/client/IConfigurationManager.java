/**
 * 
 */
package com.vanstone.configuration.client;

/**
 * 客户端配置管理器
 * @author shipeng
 */
public interface IConfigurationManager {
	
	/**
	 * 启动
	 */
	void start();
	
	/**
	 * 关闭
	 */
	void close();
	
	/**
	 * 获取值
	 * 此方法为线程安全方法 
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	String getValue(String groupId,String dataId);
	
	/**
	 * 刷新本地值
	 */
	void refreshAllLocalValue();
	
	/**
	 * 获取默认分组下的值
	 * @param dataId
	 * @return
	 */
	String getValue(String dataId);
	
	/**
	 * 获取整型值
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	Integer getIntegerValue(String groupId, String dataId);
	
	/**
	 * 获取默认分组下的Integer值
	 * @param dataId
	 * @return
	 */
	Integer getIntegerValue(String dataId);
	
	/**
	 * 获取Double值
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	Double getDoubleValue(String groupId, String dataId);
	
	/**
	 * 获取Double值
	 * @param dataId
	 * @return
	 */
	Double getDoubleValue(String dataId);
	
}
