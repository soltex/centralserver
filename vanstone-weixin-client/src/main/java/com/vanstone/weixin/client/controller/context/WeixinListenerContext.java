/**
 * 
 */
package com.vanstone.weixin.client.controller.context;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.weixin.client.WeixinClientFactory;
import com.vanstone.weixin.client.controller.repo.WeixinListenerRepo;
import com.vanstone.weixin.client.listener.WeixinLogListener;

/**
 * @author shipeng
 */
public class WeixinListenerContext implements ServletContextListener {

	private static Logger LOG = LoggerFactory.getLogger(WeixinListenerContext.class);
	
	/**当前初始化注册的Listener Class Name*/
	private List<String> classNames = new ArrayList<String>();
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		String strWeixinListeners = servletContextEvent.getServletContext().getInitParameter("weixinListeners");
		WeixinListenerRepo instance = WeixinListenerRepo.getInstance();
		
		if (strWeixinListeners != null && strWeixinListeners.length() >0) {
			LOG.info("Start Registe Weixin Listener");
			String[] weixinListeners = StringUtils.split(strWeixinListeners, ",");
			for (String classname : weixinListeners) {
				if (classname == null || classname.equals("") || classname.trim().equals("")) {
					continue;
				}
				instance.register(classname);
				classNames.add(classname);
				LOG.info("Registe Weixin Listener : " + classname + " ok.");
			}
			LOG.info("Registe All Weixin Listeners in repo.");
		}else{
			classNames.add(WeixinLogListener.class.getName());
			LOG.warn("No Weixin Listeners Registe in Repo -> Regsite Default Listener into Repo : " + WeixinLogListener.class.getName());
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		this.classNames.clear();
		WeixinListenerRepo instance = WeixinListenerRepo.getInstance();
		instance.clearWeixinListeners();
		WeixinClientFactory.getWeixinAPIManager().close();
	}
	
}
