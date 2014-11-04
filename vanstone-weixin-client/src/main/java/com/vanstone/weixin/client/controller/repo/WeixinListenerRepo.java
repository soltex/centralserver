/**
 * 
 */
package com.vanstone.weixin.client.controller.repo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.weixin.client.listener.IWeixinListener;

/**
 * @author shipeng
 */
public class WeixinListenerRepo {
	
	private static Logger LOG = LoggerFactory.getLogger(WeixinListenerRepo.class);
	
	private static class WeixinListenerRepoInstance {
		private static final WeixinListenerRepo instance = new WeixinListenerRepo();
	}
	
	/**
	 * 全局事件监听器注册容器
	 */
	private Map<String, IWeixinListener> listenerMap = new LinkedHashMap<String, IWeixinListener>();
	
	/**
	 * 获取WeixinListenerRepository实例
	 * 
	 * @return
	 */
	public static WeixinListenerRepo getInstance() {
		return WeixinListenerRepoInstance.instance;
	}
	
	/**
	 * 注册监听器
	 * 
	 * @param listenerClass
	 */
	public void register(String listenerClass) {
		try {
			IWeixinListener listener = (IWeixinListener) Class.forName(listenerClass).newInstance();
			if (!listenerMap.containsKey(listenerClass)) {
				this.listenerMap.put(listenerClass, listener);
				LOG.info("Registe Listener class in repo.");
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 获取以注册的Listener
	 * 
	 * @return
	 */
	public Collection<IWeixinListener> getRegistedListeners() {
		return this.listenerMap.values();
	}
	
	/**
	 * 获取微信监听器
	 * @param classname
	 * @return
	 */
	public IWeixinListener getWeixinListener(String classname) {
		return this.listenerMap.get(classname);
	}
	
	/**
	 * 清理仓库内的WeixinListener
	 */
	public void clearWeixinListeners() {
		this.listenerMap.clear();
		LOG.info("Clear All Weixin Listener register in context repo.");
	}
	
	/**
	 * 是否存在Listener注册在Repo中
	 * @return
	 */
	public boolean existRepo() {
		return this.listenerMap.size() != 0;
	}
}
