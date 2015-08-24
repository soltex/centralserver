/**
 * 
 */
package com.vanstone.weixin.corp.client;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;
import com.vanstone.weixin.corp.client.conf.IConfInitiator;
import com.vanstone.weixin.corp.client.conf.xml.DefaultXmlConfInitiator;

/**
 * @author shipeng
 *
 */
public class CorpWeixinServletContextListener implements ServletContextListener {
	
	public IConfInitiator getConfInitator() {
		return new DefaultXmlConfInitiator();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		getConfInitator().initial();
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		try {
			weixinCorpClientManager.getAccessToken(CorpClientConf.getInstance().getCorp());
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
