/**
 * 
 */
package com.vanstone.centralserver.common.cache;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.vanstone.centralserver.common.MyAssert;

/**
 * @author shipeng
 */
public class SimpleCache<T extends Object> {
	
	private ConcurrentHashMap<String, CacheData<T>> cache = new ConcurrentHashMap<String, CacheData<T>>();
	
	/**默认失效时间*/
	private static final long DEFAULT_EXPIRE_TIME_IN_SECOND = 7000;
	
	/**
	 * 写入缓冲
	 * @param key
	 * @param object
	 * @param expireInSecond
	 */
	public void put(String key, T object, long expireInSecond) {
		MyAssert.hasText(key);
		CacheData<T> cacheData = new CacheData<T>(object, new Date().getTime() + expireInSecond*1000);
		this.cache.put(key, cacheData);
	}
	
	/**
	 * 写入缓冲
	 * @param key
	 * @param object
	 */
	public void put(String key, T object) {
		this.put(key, object, DEFAULT_EXPIRE_TIME_IN_SECOND);
	}
	
	/**
	 * 获取缓冲数据
	 * @param key
	 * @return
	 */
	public T get(String key) {
		CacheData<T> cacheData = cache.get(key);
		if (cacheData != null) {
			return cache.get(key).getObject();
		}
		return null;
	}
	
	/**
	 * 判断是否包含Key
	 * @param key
	 * @return
	 */
	public boolean containKey(String key) {
		MyAssert.hasText(key);
		return cache.containsKey(key);
	}
	
	/**
	 * 清理全部数据
	 */
	public void clear() {
		this.cache.clear();
	}
	
	/**
	 * 缓冲数据
	 * @param <T>
	 */
	private static class CacheData<T> {
		private long expireTime;
		private T object;
		
		public CacheData(T object, long expireTime) {
			this.object = object;
			this.expireTime = expireTime;
		}
		
		public T getObject() {
			if (expireTime >= new Date().getTime()) {
				return null;
			}
			return object;
		}
		
	}
}
