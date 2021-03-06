/**
 * 
 */
package com.vanstone.centralserver.weixin.sdk.impl;

import java.util.Collection;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.vanstone.centralserver.business.sdk.weixin.AppnameExistsException;
import com.vanstone.centralserver.business.sdk.weixin.FlushResult;
import com.vanstone.centralserver.business.sdk.weixin.IWeixinInfo;
import com.vanstone.centralserver.business.sdk.weixin.IWeixinServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.weixin.AppDevInfo;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.token.UserToken;
import com.vanstone.centralserver.weixin.sdk.services.IPersistenceService;
import com.vanstone.centralserver.weixin.sdk.services.IUserTokenService;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.common.util.web.PageUtil;
import com.vanstone.zk.ZKManager;

/**
 * @author shipengpipi@126.com
 */
@Service("weixinServiceMgr")
@Validated
public class WeixinServiceMgrImpl implements IWeixinServiceMgr {

	private static Logger LOG = LoggerFactory.getLogger(WeixinServiceMgrImpl.class);

	@Autowired
	private IUserTokenService userTokenService;
	@Autowired
	private IPersistenceService persistenceService;

	@Override
	public IWeixinInfo addWeixinInfo(IWeixinInfo weixinInfo) throws WeixinException, AppnameExistsException {
		// LoadAccessToken
		UserToken userToken = this.userTokenService.loadUserTokenFromWebServer(weixinInfo.getAppid(), weixinInfo.getAppSecret());
		// 写入DB
		weixinInfo = this.persistenceService.addWeixinInfo(weixinInfo);
		weixinInfo = this.persistenceService.updateAccessToken(weixinInfo.getId(), userToken.getAccessToken());
		// 写入ZK
		AppDevInfo appDevInfo = new AppDevInfo();
		appDevInfo.setAppid(weixinInfo.getAppid());
		appDevInfo.setAccessToken(userToken.getAccessToken());
		appDevInfo.setAppname(weixinInfo.getId());
		appDevInfo.setSecret(weixinInfo.getAppSecret());
		ZKManager.getInstance().setNodeValue(Constants.getAppnameNode(weixinInfo.getId()), appDevInfo.toJsonString());
		return weixinInfo;
	}

	@Override
	public IWeixinInfo updateWeixinInfo(IWeixinInfo weixinInfo) throws WeixinException {
		UserToken userToken = this.userTokenService.loadUserTokenFromWebServer(weixinInfo.getAppid(), weixinInfo.getAppSecret());
		// 写入DB
		weixinInfo = this.persistenceService.updateWeixinInfo(weixinInfo);
		weixinInfo = this.persistenceService.updateAccessToken(weixinInfo.getId(), userToken.getAccessToken());

		AppDevInfo appDevInfo = new AppDevInfo();
		appDevInfo.setAppid(weixinInfo.getAppid());
		appDevInfo.setAccessToken(userToken.getAccessToken());
		appDevInfo.setAppname(weixinInfo.getId());
		appDevInfo.setSecret(weixinInfo.getAppSecret());

		// 写入ZK
		ZKManager.getInstance().setNodeValue(Constants.getAppnameNode(weixinInfo.getId()), appDevInfo.toJsonString());
		return weixinInfo;
	}

	@Override
	public void deleteWeixinInfo(IWeixinInfo weixinInfo) {
		this.persistenceService.deleteWeixinInfo(weixinInfo.getId());
		// 删除ZK NODE
		if (ZKManager.getInstance().existsNode(Constants.getAppnameNode(weixinInfo.getId()))) {
			ZKManager.getInstance().deleteNode(Constants.getAppnameNode(weixinInfo.getId()));
			LOG.info("DELETE ZK NODE : " + Constants.getAppnameNode(weixinInfo.getId()));
		}
	}

	@Override
	public IWeixinInfo flushAccessToken(IWeixinInfo weixinInfo) throws WeixinException {
		UserToken userToken = this.userTokenService.loadUserTokenFromWebServer(weixinInfo.getAppid(), weixinInfo.getAppSecret());
		// 写入DB
		weixinInfo = this.persistenceService.updateAccessToken(weixinInfo.getId(), userToken.getAccessToken());

		AppDevInfo appDevInfo = new AppDevInfo();
		appDevInfo.setAppid(weixinInfo.getAppid());
		appDevInfo.setAccessToken(userToken.getAccessToken());
		appDevInfo.setAppname(weixinInfo.getId());
		appDevInfo.setSecret(weixinInfo.getAppSecret());

		// 写入ZK
		ZKManager.getInstance().setNodeValue(Constants.getAppnameNode(weixinInfo.getId()), appDevInfo.toJsonString());
		return weixinInfo;
	}

	@Override
	public synchronized FlushResult flushAllAccessToken() {
		// ExecutorService executorService =
		// Executors.newFixedThreadPool(Constants.SERVER_FLUSH_EXECUTOR_SERVICE_SIZE);
		final int allrows = this.persistenceService.getTotalWeixinInfos(null);
		PageUtil<IWeixinInfo> pageUtil = new PageUtil<IWeixinInfo>(allrows, 1, Constants.SERVER_FLUSH_PAGE_SIZE);
		int pages = pageUtil.getPages();
		final FlushResult result = new FlushResult();
		for (int i = 1; i <= pages; i++) {
			final int p = i;
			PageUtil<IWeixinInfo> pu = new PageUtil<IWeixinInfo>(allrows, p, Constants.SERVER_FLUSH_PAGE_SIZE);
			int offset = pu.getOffset();
			Collection<IWeixinInfo> weixinInfos = persistenceService.getWeixinInfos(null, offset, Constants.SERVER_FLUSH_PAGE_SIZE);
			if (weixinInfos != null && weixinInfos.size() > 0) {
				for (IWeixinInfo weixinInfo : weixinInfos) {
					try {
						flushAccessToken(weixinInfo);
					} catch (WeixinException e) {
						System.out.println(e.getErrorCode());
						e.printStackTrace();
						result.addFailWeixinInfo(weixinInfo);
					}
				}
			}
		}
		// try {
		// TimeUnit.SECONDS.sleep(1);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// executorService.shutdown();
		// while (!executorService.isTerminated()) {
		// try {
		// TimeUnit.SECONDS.sleep(Constants.DEFAULT_RETRY_SLEEP_TIME);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
		// }
		// executorService.shutdown();
		return result;
	}

	@PreDestroy
	public void close() {
		ZKManager.getInstance().close();
	}

	@Override
	public PageInfo<IWeixinInfo> getWeixinInfos(String key, int pageno, int size) {
		int rows = this.persistenceService.getTotalWeixinInfos(key);
		PageUtil<IWeixinInfo> pageUtil = new PageUtil<IWeixinInfo>(rows, pageno, size);
		int offset = pageUtil.getOffset();
		Collection<IWeixinInfo> weixinInfos = this.persistenceService.getWeixinInfos(key, offset, pageUtil.getSize());
		PageInfo<IWeixinInfo> pageInfo = pageUtil.getPageInfo();
		pageInfo.addObjects(weixinInfos);
		return pageInfo;
	}

}
