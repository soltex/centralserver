/**
 * 
 */
package com.vanstone.weixin.client;

import org.junit.Test;

import com.vanstone.centralserver.common.weixin.AppDevInfo;

/**
 * @author shipeng
 *
 */
public class AppDevInfoTest {
	
	@Test
	public void testAppDevInfo() {
		AppDevInfo appDevInfo = new AppDevInfo();
		System.out.println(appDevInfo.toJsonString());
	}
}
