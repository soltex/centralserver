package com.vanstone.centralserver.weixin.sdk.services;

import java.util.Collection;

import com.vanstone.centralserver.business.sdk.weixin.AppnameExistsException;
import com.vanstone.centralserver.business.sdk.weixin.IWeixinInfo;

/**
 * 微信持久化业务
 * @author shipengpipi@126.com
 */
public interface IPersistenceService {
	
	public static final String SERVICE = "persistenceService";
	
	/**
	 * 添加微信配置信息
	 * @param weixinInfo
	 * @return
	 * @throws AppnameExistsException
	 */
	IWeixinInfo addWeixinInfo(IWeixinInfo weixinInfo) throws AppnameExistsException;
	
	/**
	 * 更新微信基本信息
	 * @param weixinInfo
	 * @return
	 */
	IWeixinInfo updateWeixinInfo(IWeixinInfo weixinInfo);
	
	/**
	 * 删除微信信息
	 * @param id
	 */
	void deleteWeixinInfo(String id);
	
	/**
	 * 通过Id/Appname 获取微信配置信息
	 * @param id
	 * @return
	 */
	IWeixinInfo getWeixinInfo(String id);
	
	/**
	 * 通过Appid和AppSecret获取配置信息
	 * @param appid
	 * @param appSecret
	 * @return
	 */
	IWeixinInfo getWeixinInfo(String appid, String appSecret);
	
	/**
	 * 检索配置信息
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<IWeixinInfo> getWeixinInfos(String key, int offset ,int limit);
	
	/**
	 * 获取管理微信数量
	 * @param key
	 * @return
	 */
	int getTotalWeixinInfos(String key);
	
	/**
	 * 更新AccessToken值
	 * @param id
	 * @param accessToken
	 * @return
	 * @throws AppnameExistsException
	 */
	IWeixinInfo updateAccessToken(String id, String accessToken) ;
	
}
