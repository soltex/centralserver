/**
 * 
 */
package com.vanstone.centralserver.common;

import org.junit.Test;

/**
 * @author shipeng
 *
 */
public class ConstantsTest {
	
	@Test
	public void testGetGroupIdDataIdObject() {
//		String node = "/vanstone/app/configuration/weedfs/weedfs.address/value";
//		GroupIdDataIdObject object = Constants.getGroupIdDataIdObjectByNode(node);
//		System.out.println(JsonUtil.object2PrettyString(object));
		
		String url = "http://localhost:8080/";
		System.out.println(url.substring(0, url.length()-1));
	}
	
}
