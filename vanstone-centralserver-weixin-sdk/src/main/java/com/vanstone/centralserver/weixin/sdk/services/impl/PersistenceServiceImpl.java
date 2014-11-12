/**
 * 
 */
package com.vanstone.centralserver.weixin.sdk.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.centralserver.business.sdk.weixin.AppnameExistsException;
import com.vanstone.centralserver.business.sdk.weixin.IWeixinInfo;
import com.vanstone.centralserver.weixin.sdk.persistence.WeixinInfoDOMapper;
import com.vanstone.centralserver.weixin.sdk.persistence.object.WeixinInfoDOWithBLOBs;
import com.vanstone.centralserver.weixin.sdk.services.IPersistenceService;
import com.vanstone.common.util.MD5Util;
import com.vanstone.framework.business.services.DefaultBusinessService;

/**
 * @author shipengpipi@126.com
 */
@Service("persistenceService")
public class PersistenceServiceImpl extends DefaultBusinessService implements IPersistenceService {

    private static final long serialVersionUID = -6074987906916401258L;
    
	@Autowired
	private WeixinInfoDOMapper weixinInfoDOMapper;
	
	/* (non-Javadoc)
	 * @see com.vanstone.weixin.server.sdk.services.IPersistenceService#addWeixinInfo(com.vanstone.weixin.server.sdk.IWeixinInfo)
	 */
	@Override
	public IWeixinInfo addWeixinInfo(final IWeixinInfo weixinInfo) throws AppnameExistsException {
		WeixinInfoDOWithBLOBs loadModel = this.weixinInfoDOMapper.selectByPrimaryKey(weixinInfo.getId());
		if (loadModel != null) {
			throw new AppnameExistsException();
		}
		try {
			return  this.execute(new TransactionCallback<IWeixinInfo>() {
				
				@Override
                public IWeixinInfo doInTransaction(TransactionStatus status) {
					WeixinInfoDOWithBLOBs model = (WeixinInfoDOWithBLOBs)weixinInfo;
					model.setSysInsertTime(new Date());
					model.setSysUpdateTime(new Date());
					weixinInfoDOMapper.insert(model);
	                return model;
                }
				
			});
		}catch (Exception e) {
			e.printStackTrace();
			throw new AppnameExistsException();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.weixin.server.sdk.services.IPersistenceService#updateWeixinInfo(com.vanstone.weixin.server.sdk.IWeixinInfo)
	 */
	@Override
	public IWeixinInfo updateWeixinInfo(final IWeixinInfo weixinInfo) {
		MyAssert4Business.notNull(weixinInfo);
		WeixinInfoDOWithBLOBs model = this.weixinInfoDOMapper.selectByPrimaryKey(weixinInfo.getId());
		if (model == null) {
			throw new IllegalArgumentException();
		}
		return this.execute(new TransactionCallback<IWeixinInfo>() {
			@Override
            public IWeixinInfo doInTransaction(TransactionStatus status) {
				WeixinInfoDOWithBLOBs model = (WeixinInfoDOWithBLOBs)weixinInfo;
				model.setSysUpdateTime(new Date());
				weixinInfoDOMapper.updateBaseWeixinInfo(model);
	            return getWeixinInfo(weixinInfo.getId());
            }
		});
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.weixin.server.sdk.services.IPersistenceService#deleteWeixinInfo(java.lang.String)
	 */
	@Override
	public void deleteWeixinInfo(final String id) {
		MyAssert4Business.hasText(id);
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				weixinInfoDOMapper.deleteByPrimaryKey(id);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.weixin.server.sdk.services.IPersistenceService#getWeixinInfo(java.lang.String)
	 */
	@Override
	public IWeixinInfo getWeixinInfo(String id) {
		return this.weixinInfoDOMapper.selectByPrimaryKey(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.weixin.server.sdk.services.IPersistenceService#getWeixinInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public IWeixinInfo getWeixinInfo(final String appid, final String appSecret) {
		return this.weixinInfoDOMapper.selectBy_Appid_AppSecret(appid, appSecret);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.weixin.server.sdk.services.IPersistenceService#getWeixinInfos(java.lang.String, int, int)
	 */
	@Override
	public Collection<IWeixinInfo> getWeixinInfos(String key, int offset, int limit) {
		List<WeixinInfoDOWithBLOBs> models = this.weixinInfoDOMapper.selectByKey(key, new RowBounds(offset, limit));
		if (models == null || models.size() <=0) {
			return  null;
		}
		Collection<IWeixinInfo> weixinInfos = new ArrayList<IWeixinInfo>();
		for (WeixinInfoDOWithBLOBs model : models) {
			weixinInfos.add(model);
		}
		return weixinInfos;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.weixin.server.sdk.services.IPersistenceService#getTotalWeixinInfos(java.lang.String)
	 */
	@Override
	public int getTotalWeixinInfos(String key) {
		return this.weixinInfoDOMapper.selectCountByKey(key);
	}
	
	@Override
    public IWeixinInfo updateAccessToken(final String id, final String accessToken) {
		MyAssert4Business.hasText(id);
		MyAssert4Business.hasText(accessToken);
		IWeixinInfo weixinInfo = this.getWeixinInfo(id);
		if (weixinInfo == null) {
			throw new IllegalArgumentException();
		}
		
		this.execute(new TransactionCallback<IWeixinInfo>() {
			@Override
            public IWeixinInfo doInTransaction(TransactionStatus status) {
				WeixinInfoDOWithBLOBs model = new WeixinInfoDOWithBLOBs();
				model.setId(id);
				model.setAccessToken(accessToken);
				model.setAccessTokenMd5(MD5Util.MD5(accessToken));
				model.setLastRetrievalTokenTime(new Date());
				weixinInfoDOMapper.updateByPrimaryKeySelective(model);
				return model;
            }
		});
		return this.getWeixinInfo(id);
    }
	
}
