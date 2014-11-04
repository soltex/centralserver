/**
 * 
 */
package com.vanstone.weixin.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author shipeng
 *
 */
public class JsonUtilTest {
	
	@Test
	public void testJsonUtilToJson() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2e8f4c073e52f7e8&redirect_uri=http%3A%2F%2Fweixin-service.c-cap.com.cn%2Fjiujuyayuan%2Four-staffs.jhtml&response_type=code&scope=snsapi_base&state=#wechat_redirect");
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		System.out.println(map.get("url"));
		System.out.println(gson.toJson(map));
	}
}
