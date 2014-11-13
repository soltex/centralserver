/**
 * 
 */
package com.vanstone.weixin.client;

import java.util.Collection;

import com.google.gson.Gson;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.user.UserGroupInfo;

/**
 * @author shipeng
 *
 */
public class UserGroupInfoMainApp {

	/**
	 * @param args
	 * @throws WeixinException 
	 */
	public static void main(String[] args) throws WeixinException {
		IWeixinAPIManager weixinAPIManager  = WeixinClientFactory.getWeixinAPIManager();
		Collection<UserGroupInfo> userGroupInfos = weixinAPIManager.getUserGroupInfos("jiujuyayuan");
		for (UserGroupInfo info : userGroupInfos) {
			Gson gson = new Gson();
			System.out.println(gson.toJson(info));
		}
	}

}
