/**
 * 
 */
package com.vanstone.weixin.client;

import com.vanstone.centralserver.common.weixin.WeixinException;

/**
 * @author shipeng
 *
 */
public class ShortUrlMain {

	/**
	 * @param args
	 * @throws WeixinException 
	 */
	public static void main(String[] args) throws WeixinException {
		IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
		String url = weixinAPIManager.buildShortUrl("sagacityidea", "http://www.baidu.com/test-userservice/aaa/bb/ccddd?shipeng=123123123123");
		System.out.println(url);
	}

}
