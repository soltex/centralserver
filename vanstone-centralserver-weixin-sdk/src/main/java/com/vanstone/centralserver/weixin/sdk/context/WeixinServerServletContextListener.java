/**
 * 
 */
package com.vanstone.centralserver.weixin.sdk.context;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.centralserver.business.sdk.weixin.IWeixinServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.framework.business.ServiceManagerFactory;

/**
 * @author shipengpipi@126.com
 */
public class WeixinServerServletContextListener implements ServletContextListener {

	private static Logger LOG = LoggerFactory.getLogger(WeixinServerServletContextListener.class);
	
	private ScheduledExecutorService scheduledExecutorService;
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("Weixin Server context start.");
		final IWeixinServiceMgr weixinManager = ServiceManagerFactory.getInstance().getService(IWeixinServiceMgr.SERVICE);
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		weixinManager.flushAllAccessToken();
		LOG.info("Finish all accesstoken to zk.");
		this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				weixinManager.flushAllAccessToken();
			}
		}, Constants.ACCESS_TOKEN_SCHEDULE_PERIOD, Constants.ACCESS_TOKEN_SCHEDULE_PERIOD, TimeUnit.SECONDS);
		LOG.info("Start schedule Executor Server.");
		LOG.info("Weixin Server context initial ok.");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		this.scheduledExecutorService.shutdown();
	}
	
}
