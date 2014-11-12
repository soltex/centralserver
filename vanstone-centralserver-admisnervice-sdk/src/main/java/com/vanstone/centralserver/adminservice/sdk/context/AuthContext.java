/**
 * 
 */
package com.vanstone.centralserver.adminservice.sdk.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.business.sdk.adminservice.Admin;
import com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.framework.business.ServiceManagerFactory;

/**
 * @author shipeng
 */
public class AuthContext implements ServletContextListener {

	private static Logger LOG = LoggerFactory.getLogger(AuthContext.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		IAuthServiceMgr authServiceMgr = ServiceManagerFactory.getInstance().getService(IAuthServiceMgr.SERVICE);
		Admin admin = authServiceMgr.getAdminByAdminName(Constants.DEFAULT_ADMIN_NAME);
		if (admin == null) {
			try {
				authServiceMgr.addAdmin(Constants.DEFAULT_ADMIN_NAME, Constants.DEFAULT_ADMIN_PWD, null);
			} catch (ObjectDuplicateException e) {
				e.printStackTrace();
			}
			LOG.info("Add Default Admin to DB. {}", Constants.DEFAULT_ADMIN_NAME);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
}
