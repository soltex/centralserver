/**
 * 
 */
package com.vanstone.weixin.client.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.conf.VanstoneConf;
import com.vanstone.centralserver.common.weixin.AppDevInfo;
import com.vanstone.centralserver.common.zk.CuratorFrameworkBuilder;
import com.vanstone.centralserver.common.zk.ZKManager;

/**
 * 订阅者
 * 
 * @author shipeng
 */
public class AccessTokenSubscriber {

	private static Logger LOG = LoggerFactory.getLogger(AccessTokenSubscriber.class);
	
	/** Local cache */
	private final ConcurrentHashMap<String, AppDevInfo> localCache = new ConcurrentHashMap<String, AppDevInfo>();
	/** Curator Framework Of ZK */
	private CuratorFramework curatorFramework;
	/** 节点监听 */
	private ConcurrentHashMap<String, PathChildrenCache> localPathChildrenCacheMap = new ConcurrentHashMap<String, PathChildrenCache>();
	/** 客户端调度器 */
	private ScheduledExecutorService scheduledExecutorService;

	/** 是否启动 */
	private boolean start = false;

	private static class SubscriberInstance {
		private static final AccessTokenSubscriber instance = new AccessTokenSubscriber();
		static {
			if (VanstoneConf.getInstance().containAppnames()) {
				instance.start();
			}else{
				LOG.warn("CuratorFramework not iniital, Vanstone conf not exists.");
			}
		}
	}

	private AccessTokenSubscriber() {
		if (VanstoneConf.getInstance().containAppnames()) {
			curatorFramework = CuratorFrameworkBuilder.createCuratorFramework(VanstoneConf.getInstance().getZk(),
					VanstoneConf.getInstance().getZkConnectionTimeoutMS());
		}
	}

	public void start() {
		if (curatorFramework == null) {
			throw new ExceptionInInitializerError("curatorframework initial fail.");
		}
		curatorFramework.start();
		// 初始化LocalCache
		this.initLocalCache();
		// 初始化NodeListener
		this.postNodeListener();
		start = true;
		LOG.info("Access Token Subscriber initial ok.");
	}

	/**
	 * 关闭ZK
	 */
	public void close() {
		if (this.curatorFramework != null) {
			this.curatorFramework.close();
		}
		if (localPathChildrenCacheMap != null && localPathChildrenCacheMap.size() > 0) {
			for (PathChildrenCache pathChildrenCache : localPathChildrenCacheMap.values()) {
				try {
					pathChildrenCache.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()
				&& !scheduledExecutorService.isTerminated()) {
			scheduledExecutorService.shutdown();
		}
	}

	private void initLocalCache() {
		Collection<String> appnames = VanstoneConf.getInstance().getAppnames();
		if (appnames != null && appnames.size() > 0) {
			for (String id : appnames) {
				String value = ZKManager.getInstance().getNodeValue(curatorFramework, Constants.getAppnameNode(id));
				if (value == null || "".equals(value)) {
					continue;
				}
				AppDevInfo appDevInfo = AppDevInfo.parseAppDevInfoByJson(value);
				localCache.put(id, appDevInfo);
				LOG.info("Initial Local cache appname : " + id + " value : " + appDevInfo.toString());
			}
		}
		// 初始话调度器
		this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		this.scheduledExecutorService
				.scheduleAtFixedRate(
						new Runnable() {
							@Override
							public void run() {
								Collection<String> appnames = VanstoneConf.getInstance().getAppnames();
								if (appnames != null && appnames.size() > 0) {
									for (String id : appnames) {
										String value = ZKManager.getInstance().getNodeValue(curatorFramework,
												Constants.getAppnameNode(id));
										if (value == null || "".equals(value)) {
											continue;
										}
										AppDevInfo appDevInfo = AppDevInfo.parseAppDevInfoByJson(value);
										localCache.put(id, appDevInfo);
										LOG.info("Client Scheduler update Local cache appname : " + id + " value : " + appDevInfo.toString());
									}
								}
							}
						}, Constants.CLIENT_SCHEDULER_PERIOD_IN_SENCOND, Constants.CLIENT_SCHEDULER_PERIOD_IN_SENCOND,
						TimeUnit.SECONDS);
	}

	/**
	 * 监听器注册
	 */
	private void postNodeListener() {
		Collection<String> appnames = VanstoneConf.getInstance().getAppnames();
		if (appnames == null || appnames.size() <= 0) {
			LOG.warn("Current Not found Appnames, if you want use advance function of weixin, please provide appname and register it in weixin server.");
			return;
		}
		// Initial PathChildCache
		for (String id : appnames) {
			final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,
					Constants.getClientMonitorPath(id), true);
			LOG.info("start monitor node : " + Constants.getClientMonitorPath(id));

			localPathChildrenCacheMap.put(id, pathChildrenCache);
			pathChildrenCache.getListenable().addListener(newPathChildrenCacheListener(pathChildrenCache));
			try {
				pathChildrenCache.start(StartMode.BUILD_INITIAL_CACHE);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
	}

	/**
	 * 新建监听器
	 * 
	 * @param pathChildrenCache
	 * @return
	 */
	private PathChildrenCacheListener newPathChildrenCacheListener(final PathChildrenCache pathChildrenCache) {
		return new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
				PathChildrenCacheEvent.Type eventType = event.getType();
				LOG.info("EVENT TYPE " + eventType);
				
				String appname = Constants.getAppnameByNode(event.getData().getPath());

				if (eventType.equals(PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED)) {
					pathChildrenCache.rebuild();
					return;
				}

				// 数据更新事件
				if (eventType.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
					// 节点数据更新
					String value = new String(event.getData().getData(), Constants.SYS_CHARSET_UTF8);
					AppDevInfo appDevInfo = AppDevInfo.parseAppDevInfoByJson(value);
					localCache.put(appname, appDevInfo);
					LOG.info("Appname " + appname + "Data update to " + value);
					return;
				}
				// 数据删除事件
				if (eventType.equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
					localCache.remove(appname);
					LOG.info("Appname has been deleted : " + appname);
					return;
				}
				// 数据添加事件
				if (eventType.equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
					String value = new String(event.getData().getData(), Constants.SYS_CHARSET_UTF8);
					AppDevInfo appDevInfo = AppDevInfo.parseAppDevInfoByJson(value);
					localCache.put(appname, appDevInfo);
					LOG.info("Appname has been Add : " + appname);
					return;
				}
				LOG.info("Default event : " + event.toString());
			}
		};
	}

	/**
	 * 获取Appname
	 * 
	 * @param appname
	 * @return
	 */
	public String getAccessToken(String appname) {
		if (!isStart()) {
			throw new IllegalArgumentException();
		}
		MyAssert.hasText(appname);
		return localCache.get(appname).getAccessToken();
	}
	
	/**
	 * 获取AppDevInfo
	 * @param appname
	 * @return
	 */
	public AppDevInfo getAppDevInfo(String appname) {
		if (!isStart()) {
			throw new IllegalArgumentException();
		}
		MyAssert.hasText(appname);
		return localCache.get(appname);
	}
	
	/**
	 * 是否已经启动
	 * 
	 * @return
	 */
	public boolean isStart() {
		return start;
	}

	/**
	 * 获取订阅者单例
	 * 
	 * @return
	 */
	public static AccessTokenSubscriber getInstance() {
		return SubscriberInstance.instance;
	}

	/**
	 * 动态添加Appname，并增加监控点
	 * 
	 * @param appname
	 */
	public void addAppname(String appname) {
		MyAssert.hasText(appname);
		if (localCache.contains(appname)) {
			LOG.info("The Appname  " + appname + " has been cached in local.");
			return;
		}
		String value = ZKManager.getInstance().getNodeValue(curatorFramework, Constants.getAppnameNode(appname));
		if (value == null || "".equals(value)) {
			throw new IllegalArgumentException("Appname'value not found in zk node.");
		}
		AppDevInfo appDevInfo = AppDevInfo.parseAppDevInfoByJson(value);
		localCache.put(appname, appDevInfo);
		LOG.info("Load ZK Node value into Localcache");
		// Add listener in node
		final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,
				Constants.getClientMonitorPath(appname), true);
		LOG.info("start monitor node : " + Constants.getClientMonitorPath(appname));
		localPathChildrenCacheMap.put(appname, pathChildrenCache);
		pathChildrenCache.getListenable().addListener(newPathChildrenCacheListener(pathChildrenCache));
		try {
			pathChildrenCache.start(StartMode.BUILD_INITIAL_CACHE);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 清除掉Appname
	 * 
	 * @param appname
	 */
	public void removeAppname(String appname) {
		MyAssert.hasText(appname);
		if (!localCache.containsKey(appname)) {
			throw new IllegalArgumentException();
		}
		localCache.remove(appname);
		PathChildrenCache pathChildrenCache = this.localPathChildrenCacheMap.get(appname);
		if (pathChildrenCache != null) {
			try {
				pathChildrenCache.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		localPathChildrenCacheMap.remove(appname);
	}

	/**
	 * 清理本地注册的Appnames
	 */
	public void clearAppnames() {
		// clear local cache
		this.localCache.clear();
		// clear and stop pathcachelistener
		if (this.localPathChildrenCacheMap != null && this.localPathChildrenCacheMap.size() > 0) {
			for (PathChildrenCache pathChildrenCache : this.localPathChildrenCacheMap.values()) {
				try {
					pathChildrenCache.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					pathChildrenCache = null;
				}
			}
		}
		this.localPathChildrenCacheMap.clear();
		LOG.info("Clear All Appnames in local.");
	}
}
