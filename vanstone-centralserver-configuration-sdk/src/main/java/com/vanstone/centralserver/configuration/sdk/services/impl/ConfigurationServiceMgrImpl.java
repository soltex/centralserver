package com.vanstone.centralserver.configuration.sdk.services.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.CollectionUtils;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.business.sdk.AbstractBaseBusinessSDKService;
import com.vanstone.centralserver.business.sdk.configuration.IConfInfo;
import com.vanstone.centralserver.business.sdk.configuration.IConfigurationServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.conf.VanstoneConf;
import com.vanstone.centralserver.common.zk.CuratorFrameworkBuilder;
import com.vanstone.centralserver.common.zk.ZKManager;
import com.vanstone.centralserver.configuration.sdk.persistence.SysConfInfoDOMapper;
import com.vanstone.centralserver.configuration.sdk.persistence.object.SysConfInfoDO;
import com.vanstone.common.util.MD5Util;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.common.util.web.PageUtil;

/**
 * ConfigurationServiceImpl
 */
@Service("configurationService")
public class ConfigurationServiceMgrImpl extends AbstractBaseBusinessSDKService implements IConfigurationServiceMgr {
	
    private static final long serialVersionUID = -5530903674093816970L;
    
    private static Log LOG = LogFactory.getLog(ConfigurationServiceMgrImpl.class);
    /**ZK CuratorFramework*/
    private CuratorFramework curatorFramework = CuratorFrameworkBuilder.createCuratorFrameworkAndStart(VanstoneConf.getInstance().getZk(), VanstoneConf.getInstance().getZkConnectionTimeoutMS());
    
    @Autowired
    private SysConfInfoDOMapper sysConfInfoDOMapper;
    
	@Override
    public String getValue(String groupid, String dataid) {
		MyAssert4Business.hasText(dataid);
		groupid = _parseGroupId(groupid);
		//load from db
		final SysConfInfoDO model = this.sysConfInfoDOMapper.selectByDataId_GroupId(groupid, dataid);
		if (model == null) {
			return null;
		}
		return model.getConfValue();
    }
	
	@Override
    public Integer getIntValue(String groupid, String dataid) {
		String value = this.getValue(groupid, dataid);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
	    return Integer.parseInt(value);
    }

	@Override
    public Double getDoubleValue(String groupid, String dataid) {
		String value = this.getValue(groupid, dataid);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Double.parseDouble(value);
    }
	
	@Override
    public Float getFloatValue(String groupid, String dataid) {
		String value = this.getValue(groupid, dataid);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Float.parseFloat(value);
    }

	@Override
    public void addConf(final String groupId, final String dataId, final String value) throws ObjectDuplicateException {
		MyAssert4Business.hasText(dataId);
		MyAssert4Business.hasText(value);
		
		SysConfInfoDO sysConfInfoDO = this.sysConfInfoDOMapper.selectByDataId_GroupId(groupId, dataId);
		if (sysConfInfoDO != null) {
			throw new ObjectDuplicateException();
		}
		try {
	        this.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					SysConfInfoDO model = new SysConfInfoDO();
					model.setGroupId(_parseGroupId(groupId));
					model.setDataId(dataId);
					model.setConfValue(value);
					model.setConfValueMd5(MD5Util.MD5(value));
					model.setSysInsertTime(new Date());
					model.setSysUpdateTime(new Date());
					sysConfInfoDOMapper.insert(model);
				}
			});
        } catch (Exception e) {
        	//当并发过大时，key有可能会重复
        	throw new ObjectDuplicateException();
        }
		ZKManager.getInstance().setNodeValue(this.curatorFramework, Constants.getConfigurationNode(groupId, dataId), value);
		LOG.info("Add Configuration ok :" + groupId + " : " + dataId + " : " +  value);
    }
	
	@Override
    public void updateConf(final int id, final String groupId, final String dataId, final String value) throws ObjectDuplicateException {
		MyAssert4Business.hasText(dataId);
		MyAssert4Business.hasText(value);
		final SysConfInfoDO sysConfInfoDO = this.sysConfInfoDOMapper.selectByPrimaryKey(id);
		if (sysConfInfoDO == null) {
			throw new IllegalArgumentException();
		}
		if (this.sysConfInfoDOMapper.selectByDataId_GroupId_NotSelf(groupId, dataId, id) != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				sysConfInfoDO.setConfValue(value);
				sysConfInfoDO.setConfValueMd5(MD5Util.MD5(value));
				sysConfInfoDO.setDataId(dataId);
				sysConfInfoDO.setGroupId(groupId);
				sysConfInfoDO.setSysUpdateTime(new Date());
				sysConfInfoDOMapper.updateByPrimaryKeyWithBLOBs(sysConfInfoDO);
			}
		});
		ZKManager.getInstance().deleteNode(this.curatorFramework, Constants.getConfigurationNode(sysConfInfoDO.getGroupId(), sysConfInfoDO.getDataId()));
		ZKManager.getInstance().setNodeValue(this.curatorFramework, Constants.getConfigurationNode(groupId, dataId), value);
    }
	
	@Override
	public void updateConf(final String groupId, final String dataId, final String value) {
		MyAssert4Business.hasText(groupId);
		MyAssert4Business.hasText(dataId);
		MyAssert4Business.hasText(value);
		
		final SysConfInfoDO sysConfInfoDO = this.sysConfInfoDOMapper.selectByDataId_GroupId(groupId, dataId);
		if (sysConfInfoDO == null) {
			LOG.error(new StringBuffer().append("Load conf not found [").append(groupId).append("] [").append(dataId).append("]"));
			return;
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				SysConfInfoDO model = new SysConfInfoDO();
				model.setId(sysConfInfoDO.getId());
				model.setConfValue(value);
				model.setConfValueMd5(MD5Util.MD5(value));
				model.setSysUpdateTime(new Date());
				sysConfInfoDOMapper.updateByPrimaryKeySelective(model);
			}
		});
		ZKManager.getInstance().setNodeValue(this.curatorFramework, Constants.getConfigurationNode(groupId, dataId), value);
	}
	
	@Override
    public void deleteConf(final String groupId, final String dataId) {
		MyAssert4Business.hasText(dataId);
		String value = this.getValue(_parseGroupId(groupId), dataId);
		if (StringUtils.isEmpty(value)) {
			return;
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				sysConfInfoDOMapper.deleteByGroupId_DataId(groupId, dataId);
			}
		});
		ZKManager.getInstance().deleteNode(curatorFramework, Constants.getConfigurationNode(groupId, dataId));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteConfsByGroupId(String groupId) {
		MyAssert4Business.hasText(groupId);
		Collection<IConfInfo> confInfos = this.getConfsByGroupId(groupId);
		if (!CollectionUtils.isEmpty(confInfos)) {
			for (IConfInfo confInfo : confInfos) {
				this.deleteConf(confInfo.getGroupId(), confInfo.getDataId());
			}
		}
		ZKManager.getInstance().deleteNode(curatorFramework, Constants.getConfigurationNode(groupId));
		LOG.info("Delete Confs By GroupId : " + groupId);
	}
	
	@Override
    public Collection<String> getGroups() {
		return this.sysConfInfoDOMapper.selectGroupByGroupId();
    }
	
	@SuppressWarnings("rawtypes")
    @Override
    public Collection getConfsByGroupId(String groupId) {
		MyAssert4Business.hasText(groupId);
		return this.sysConfInfoDOMapper.selectByGroupId(groupId);
	}
	
	@SuppressWarnings("rawtypes")
    @Override
    public Collection getConfs(String key, int offset, int limit) {
		return this.sysConfInfoDOMapper.selectByKey(key, new RowBounds(offset, limit));
    }
    
	@Override
	public int getTotalConfs(String key) {
		return this.sysConfInfoDOMapper.selectTotalByKey(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<IConfInfo> getConfInfosPageInfo(String key, int p, int pagesize) {
		int allrows = this.getTotalConfs(key);
		PageUtil<IConfInfo> pageUtil = new PageUtil<IConfInfo>(allrows, p, pagesize);
		Collection<IConfInfo> confInfos = this.getConfs(key, pageUtil.getOffset(), pageUtil.getSize());
		PageInfo<IConfInfo> pageInfo = pageUtil.getPageInfo();
		pageInfo.addObjects(confInfos);
		return pageInfo;
	}
	
	@Override
    public void refreshAllConfs() {
		List<SysConfInfoDO> sysConfInfoDOs = this.sysConfInfoDOMapper.selectAll();
		if (CollectionUtils.isEmpty(sysConfInfoDOs)) {
			return;
		}
		ZKManager zkManager = ZKManager.getInstance();
		for (SysConfInfoDO model : sysConfInfoDOs) {
			zkManager.setNodeValue(curatorFramework, Constants.getConfigurationNode(model.getGroupId(), model.getDataId()), model.getConfValue());
			LOG.info("Refresh configruation : " + ToStringBuilder.reflectionToString(model, ToStringStyle.SHORT_PREFIX_STYLE));
		}
		LOG.info("Refresh all configurations ok.");
    }
	
	@Override
	public void refreshConf(String groupId, String dataId) {
		MyAssert4Business.hasText(groupId);
		MyAssert4Business.hasText(dataId);
		String value = this.getValue(groupId, dataId);
		if (value == null || "".equals(value)) {
			return;
		}
		ZKManager.getInstance().setNodeValue(curatorFramework, Constants.getConfigurationNode(groupId, dataId), value);
	}
	
	@Override
	public void close() {
		if (curatorFramework != null) {
			curatorFramework.close();
		}
	}
	
	private String _parseGroupId(String groupId) {
		return (groupId == null || "".equals(groupId)) ? Constants.DEFAULT_GROUP_NAME : groupId;
	}
}
