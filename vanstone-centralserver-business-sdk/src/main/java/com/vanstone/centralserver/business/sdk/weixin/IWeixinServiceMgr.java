/**
 * 
 */
package com.vanstone.centralserver.business.sdk.weixin;

import javax.validation.constraints.NotNull;

import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.common.util.web.PageInfo;

/**
 * IWeixinManager 微信管理类
 * @author shipeng
 */
public interface IWeixinServiceMgr {
	
	public static final String SERVICE = "weixinServiceMgr";

	/**
	 * 添加微信信息
	 * 
	 * @param weixinInfo
	 * @param autoLoadAccessToken
	 * @return
	 * @throws AppnameExistsException
	 */
	IWeixinInfo addWeixinInfo(@NotNull IWeixinInfo weixinInfo) throws WeixinException, AppnameExistsException;
	
	/**
	 * 更新微信基本信息
	 * 
	 * @param weiInfo
	 * @return
	 * @throws WeixinException
	 * @throws AppnameExistsException
	 */
	IWeixinInfo updateWeixinInfo(@NotNull IWeixinInfo weixinInfo) throws WeixinException;

	/**
	 * 删除微信配置信息
	 * 
	 * @param weixinInfo
	 */
	void deleteWeixinInfo(@NotNull IWeixinInfo weixinInfo);

	/**
	 * 刷新WeixinAccessToken信息
	 * 
	 * @param weixinInfo
	 * @return
	 * @throws WeixinException
	 */
	IWeixinInfo flushAccessToken(@NotNull IWeixinInfo weixinInfo) throws WeixinException;

	/**
	 * 刷新全部AccessToken
	 */
	FlushResult flushAllAccessToken();

	/**
	 * 获取微信Weiinfos分页
	 * 
	 * @param key
	 * @param pageno
	 * @param size
	 * @return
	 */
	PageInfo<IWeixinInfo> getWeixinInfos(String key, int pageno, int size);
}
