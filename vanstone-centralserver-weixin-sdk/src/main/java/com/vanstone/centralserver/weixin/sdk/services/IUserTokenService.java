/**
 * 
 */
package com.vanstone.centralserver.weixin.sdk.services;

import com.vanstone.centralserver.business.sdk.weixin.AppnameExistsException;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.token.UserToken;

/**
 * @author shipengpipi@126.com
 */
public interface IUserTokenService {
	
	public static final String SERVICE = "userTokenService";
	
	/**
	 * 通过WeixinAPI获取UserToken,注意,每次获取UserToken将更变
	 * @param appname
	 * @throws AppnameExistsException
	 * @return
	 */
	UserToken loadUserTokenFromWebServer(String appid,String appSecret) throws WeixinException;
	
}
