/**
 * 
 */
package com.vanstone.centralserver.weixin.sdk.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.centralserver.business.sdk.weixin.IWeixinServiceMgr;
import com.vanstone.centralserver.common.Constants;

/**
 * @author shipeng
 *
 */
@Service("weixinAppRefreshManager")
public class WeixinAppRefreshManager {

	public static final String SERVICE = "weixinAppRefreshManager";
	
	private ScheduledExecutorService scheduledExecutorService;
	
	@Autowired
	private IWeixinServiceMgr weixinManager;
	
	@PostConstruct
	private void __initial() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	}
	
	public void start() {
		this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				weixinManager.flushAllAccessToken();
			}
		}, Constants.ACCESS_TOKEN_SCHEDULE_PERIOD, Constants.ACCESS_TOKEN_SCHEDULE_PERIOD, TimeUnit.SECONDS);
	}
	
}
