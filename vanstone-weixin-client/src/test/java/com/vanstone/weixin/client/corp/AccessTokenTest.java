/**
 * 
 */
package com.vanstone.weixin.client.corp;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.util.HttpClientTemplate;

/**
 * @author shipeng
 *
 */
public class AccessTokenTest {
	
	@Test
	public void testGetAccessToken() {
		String corpID = "wx87c2c0c1d9ec9f7a";
		String corpSecret = "DvNnPTVFkoQVNOh9VdjfcAnKOr_XaZCjwgxg8sNn4fHW1Co_2Thok2ymdnCjNLPq";
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=#CORPID#&corpsecret=#CORP_SECRET#";
		url = url.replaceAll("#CORPID#", corpID);
		url = url.replaceAll("#CORP_SECRET#", corpSecret);
		HttpClientTemplate clientTemplate = new HttpClientTemplate();
		HttpClient httpClient = clientTemplate.createHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String html = EntityUtils.toString(httpEntity, Constants.SYS_CHARSET_UTF8);
			System.out.println(html);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//{"access_token":"0l94xFt2d7h62t4G8Ooy8VW7LVsCcjGfbaEUUxKeQyOEqd1GKIe7CH1hilXs89pv6MlQfTuhblg5giPWCUsahA","expires_in":7200}
		//{"access_token":"0l94xFt2d7h62t4G8Ooy8VW7LVsCcjGfbaEUUxKeQyOEqd1GKIe7CH1hilXs89pv6MlQfTuhblg5giPWCUsahA","expires_in":7200}
	}
}
