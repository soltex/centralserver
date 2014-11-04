/**
 * 
 */
package com.vanstone.weixin.client;

import java.util.Collection;

import org.junit.Test;

import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.user.UserGroupInfo;

/**
 * @author shipeng
 */
public class WeixinTest {
	
	@Test
	public void testWeixinUserToken() {
		IWeixinAPIManager weixinAPIManager =WeixinClientFactory.getWeixinAPIManager();
		try {
			Collection<UserGroupInfo> userGroupInfos = weixinAPIManager.getUserGroupInfos("jiujuyayuan");
			for (UserGroupInfo info : userGroupInfos) {
				System.out.println(info.getName() + "_" + info.getCount());
			}
		} catch (WeixinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
