/**
 * 
 */
package com.vanstone.weixin.client;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * @author shipeng
 *
 */
public class JsonTest {
	
	public static void main(String[] args) {
		String[] strs = new String[]{"123","3232"};
		System.out.println(JsonUtil.object2PrettyString(strs,true));
	}
	
}
