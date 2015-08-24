/**
 * 
 */
package com.vanstone.weixin.corp.client.impl;

import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;
import com.vanstone.weixin.corp.client.conf.xml.DefaultXmlConfInitiator;

/**
 * @author shipeng
 *
 */
public class MainApp {
	
	public static void main(String[] args) {
		
		
		DefaultXmlConfInitiator initiator = new DefaultXmlConfInitiator();
		initiator.initial();
		
		CorpClientConf corpClientConf = CorpClientConf.getInstance();
		DebugUtil.print(corpClientConf);
		
		System.out.println(corpClientConf.getDefaultPassiveMsgListener());
		
	}
	
}
