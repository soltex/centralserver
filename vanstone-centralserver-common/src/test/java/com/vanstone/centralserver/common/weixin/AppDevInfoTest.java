/**
 * 
 */
package com.vanstone.centralserver.common.weixin;

import org.junit.Test;

/**
 * @author shipeng
 */
public class AppDevInfoTest {
	
	@Test
	public void testAppDevInfo() {
		AppDevInfo appDevInfo = new AppDevInfo();
		appDevInfo.setAppid("aa");
		appDevInfo.setAppname("jiujuyayuan");
		appDevInfo.setSecret("bb");
		appDevInfo.setAccessToken("sdfsdfsdfsdfsdfsdf");
		System.out.println(appDevInfo.toJsonString());
	}
	
}
