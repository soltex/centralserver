/**
 * 
 */
package com.vanstone.configuration.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.conf.VanstoneConf;
import com.vanstone.centralserver.common.configuration.GroupIdDataIdObject;
import com.vanstone.centralserver.common.zk.CuratorFrameworkBuilder;
import com.vanstone.centralserver.common.zk.ZKManager;
import com.vanstone.configuration.client.IConfigurationManager;

/**
 * @author shipeng
 *
 */
public class ConfigurationManagerImpl implements IConfigurationManager {
	
	private static Logger LOG = LoggerFactory.getLogger(ConfigurationManagerImpl.class);
	/**本地缓冲,格式为 groupId : (dataId : value)*/
	private ConcurrentHashMap<String, ConcurrentHashMap<String, String>> localCache = new ConcurrentHashMap<String, ConcurrentHashMap<String,String>>();
	/**CuratorFramework*/
	private CuratorFramework curatorFramework;
	/**节点监控器*/
	private ConcurrentHashMap<String,PathChildrenCache> pathChildrenCaches = new ConcurrentHashMap<String, PathChildrenCache>();
	/**刷新器*/
	private ScheduledExecutorService scheduledExecutorService;
	/**当前是否启动*/
	private boolean startable = false;
	
	@Override
	public void start() {
		curatorFramework = CuratorFrameworkBuilder.createCuratorFramework(VanstoneConf.getInstance().getZk(), VanstoneConf.getInstance().getZkConnectionTimeoutMS());
		curatorFramework.start();
		LOG.info("CuratorFramework initial ok.");
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Collection<GroupIdDataIdObject> objects = loadGroupIdDataIdObjectsFromLocalCache();
				if (objects != null && objects.size() >0) {
					for (GroupIdDataIdObject o : objects) {
						saveIntoLocalCache(o.getGroupId(), o.getDataId());
						LOG.info("Schedule Executor Service refresh to local cache : " + o.getGroupId() + " " + o.getDataId());
					}
				}
			}
		}, Constants.CLIENT_SCHEDULER_PERIOD_IN_SENCOND, Constants.CLIENT_SCHEDULER_PERIOD_IN_SENCOND, TimeUnit.SECONDS);
		LOG.info("ScheduledExecutorService Initial ok.");
		startable = true;
		LOG.info("Configuration Manager start.,");
	}
	
	@Override
	public void close() {
		if (curatorFramework != null) {
			curatorFramework.close();
		}
		Collection<PathChildrenCache> pathChildrenCacheSet = pathChildrenCaches.values();
		if (pathChildrenCacheSet != null && pathChildrenCacheSet.size() >0) {
			for (PathChildrenCache pathChildrenCache : pathChildrenCacheSet) {
				try {
					pathChildrenCache.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LOG.info("Configuration Manager close.");
	}
	
	@Override
	public synchronized String getValue(String groupId, String dataId) {
		MyAssert.hasText(groupId);
		MyAssert.hasText(dataId);
		if (!startable) {
			throw new IllegalArgumentException("please start configuration manager start.");
		}
		//local from local cache
		Map<String, String> dataMap = this.localCache.get(groupId);
		String value = null;
		if (dataMap != null) {
			value = dataMap.get(dataId);
		}
		if (value != null && !"".equals(value)) {
			LOG.info("Local configuration from Local cache success.");
			return value;
		}
		// save into local cache
		LOG.info("Local configuration from zk");
		return this.saveIntoLocalCache(groupId, dataId);
	}
	
	/**
	 * 从ZK中获取数据到本地LocalCache中,并返回
	 * @param groupid
	 * @param dataid
	 * @return
	 */
	private String saveIntoLocalCache(final String groupId, final String dataId) {
		String value = ZKManager.getInstance().getNodeValue(curatorFramework, Constants.getConfigurationNode(groupId, dataId));
		if (value == null) {
			return null;
		}
		//save into local cache
		ConcurrentHashMap<String, String> dataMap = localCache.get(groupId);
		if (dataMap == null) {
			dataMap = new ConcurrentHashMap<String, String>();
			this.localCache.put(groupId, dataMap);
		}
		dataMap.put(dataId, value);
		//set path child cache listener
		String key = buildPathChildCacheKey(groupId, dataId);
		PathChildrenCache loadpathChildrenCache = pathChildrenCaches.get(key);
		if (loadpathChildrenCache == null) {
			final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, Constants.getConfigurationMonitorNode(groupId, dataId), true);
			pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
				@Override
				public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
					PathChildrenCacheEvent.Type eventType = event.getType();
					if (eventType.equals(PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED)) {
						pathChildrenCache.rebuild();
						return;
					}
					String value = new String(event.getData().getData(), Constants.SYS_CHARSET_UTF8);
					if (eventType.equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
						setValueToLocalCache(groupId, dataId, value);
						LOG.info("Add Data to local cache.");
						return;
					}
					if (eventType.equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
						removeLocalCache(groupId, dataId);
						LOG.info("Remove Data from Local cache");
						return;
					}
					if (eventType.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
						setValueToLocalCache(groupId, dataId, value);
						LOG.info("Update Data to local cache.");
					}
				}
			});
			
			try {
				pathChildrenCache.start();
			} catch (Exception e) {
				e.printStackTrace();
				if (pathChildrenCache != null) {
					try {
						pathChildrenCache.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				throw new RuntimeException(e);
			} 
			pathChildrenCaches.put(key, pathChildrenCache);
		}
		return value;
	}
	
	@Override
	public synchronized void refreshAllLocalValue() {
		
	}

	@Override
	public String getValue(String dataId) {
		return this.getValue(Constants.DEFAULT_GROUP_NAME, dataId);
	}

	@Override
	public Integer getIntegerValue(String groupId, String dataId) {
		return Integer.parseInt(getValue(groupId, dataId));
	}
	
	@Override
	public Integer getIntegerValue(String dataId) {
		return this.getIntegerValue(Constants.DEFAULT_GROUP_NAME, dataId);
	}
	
	@Override
	public Double getDoubleValue(String groupId, String dataId) {
		return Double.parseDouble(this.getValue(groupId, dataId));
	}

	@Override
	public Double getDoubleValue(String dataId) {
		return this.getDoubleValue(Constants.DEFAULT_GROUP_NAME, dataId);
	}
	
	/**
	 * 建立PathChildCache的key
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	private String buildPathChildCacheKey(String groupId, String dataId) {
		return "groupid_" + groupId + "_dataid_" + dataId + "_pcckey"; 
	}
	
	private Collection<GroupIdDataIdObject> loadGroupIdDataIdObjectsFromLocalCache() {
		Set<String> groupids = this.localCache.keySet();
		if (groupids == null || groupids.size() <=0) {
			return null;
		}
		Collection<GroupIdDataIdObject> objects = new ArrayList<GroupIdDataIdObject>();
		for (String groupid : groupids) {
			ConcurrentHashMap<String, String> dataMap = localCache.get(groupid);
			if (dataMap == null || dataMap.size() <=0) {
				continue;
			}
			Set<String> dataids = dataMap.keySet();
			for (String dataid : dataids) {
				GroupIdDataIdObject object = new GroupIdDataIdObject();
				object.setGroupId(groupid);
				object.setDataId(dataid);
				objects.add(object);
			}
		}
		return objects;
	}
	
	/**
	 * 设定值到Local Cache中
	 * @param groupId
	 * @param dataId
	 * @param value
	 */
	private void setValueToLocalCache(String groupId,String dataId, String value) {
		ConcurrentHashMap<String, String> dataMap = localCache.get(groupId);
		if (dataMap == null) {
			dataMap = new ConcurrentHashMap<String, String>();
			localCache.put(groupId, dataMap);
		}
		dataMap.put(dataId, value);
	}
	
	/**
	 * 删除Local Cache中的 GroupId:DataId
	 * @param groupId
	 * @param dataId
	 */
	private void removeLocalCache(String groupId, String dataId) {
		ConcurrentHashMap<String, String> dataMap = localCache.get(groupId);
		if (dataMap == null) {
			return;
		}
		dataMap.remove(dataId);
	}
}
