/**
 * 
 */
package com.vanstone.centralserver.configuration.sdk.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.vanstone.centralserver.business.sdk.configuration.IConfigurationServiceMgr;
import com.vanstone.framework.business.ServiceManagerFactory;

/**
 * @author shipeng
 *
 */
public class ConfigurationContextListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		IConfigurationServiceMgr configurationServiceMgr = ServiceManagerFactory.getInstance().getService(IConfigurationServiceMgr.SERVICE);
		if (configurationServiceMgr != null) {
			configurationServiceMgr.close();
		}
	}

}
