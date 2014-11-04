/**
 * 
 */
package com.vanstone.centralserver.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author shipeng
 *
 */
public class URLUtil {
	
	/**
	 * URL 进行转码
	 * @param url
	 * @return
	 */
	public static String urlencode(String url) {
		MyAssert.hasText(url);
		try {
			return URLEncoder.encode(url, Constants.SYS_CHARSET_UTF8_STRING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public static void main(String[] args) {
		String url = "http://localhost:8080/aaaa.jsp?aaaa=123&sdfsdfsdf=111";
		System.out.println(urlencode(url));
	}
}