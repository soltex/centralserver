/**
 * 
 */
package com.vanstone.centralserver.adminservice.sdk.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.adminservice.sdk.persistence.AuthAdminDOMapper;
import com.vanstone.centralserver.adminservice.sdk.persistence.object.AuthAdminDO;
import com.vanstone.centralserver.business.sdk.adminservice.Admin;
import com.vanstone.centralserver.business.sdk.adminservice.AdminException;
import com.vanstone.centralserver.business.sdk.adminservice.AdminException.ErrorCode;
import com.vanstone.centralserver.business.sdk.adminservice.AdminState;
import com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.dal.id.IDBuilder;
import com.vanstone.framework.business.services.DefaultBusinessService;

/**
 * @author shipeng
 */
@Service("authServiceMgr")
@Validated
public class AuthServiceMgrImpl extends DefaultBusinessService implements IAuthServiceMgr {

	/***/
	private static final long serialVersionUID = 8347878837691076352L;
	
	private static Logger LOG = LoggerFactory.getLogger(AuthServiceMgrImpl.class);
	
	@Autowired
	private AuthAdminDOMapper authAdminDOMapper;
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr#addAdmin(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Admin addAdmin(final String adminName, final String adminPwd, final String fullName) throws ObjectDuplicateException {
		Admin admin = this.getAdminByAdminName(adminName);
		if (admin != null) {
			throw new ObjectDuplicateException();
		}
		return this.execute(new TransactionCallback<Admin>() {
			@Override
			public Admin doInTransaction(TransactionStatus arg0) {
				AuthAdminDO authAdminDO = new AuthAdminDO();
				authAdminDO.setId(IDBuilder.base58Uuid());
				authAdminDO.setAdminName(adminName);
				authAdminDO.setAdminPwd(adminPwd);
				authAdminDO.setFullName(fullName);
				authAdminDO.setSysInsertTime(new Date());
				authAdminDO.setSysUpdateTime(new Date());
				authAdminDO.setAdminState(AdminState.Active.getCode());
				authAdminDOMapper.insert(authAdminDO);
				return BeanUtil.toAdmin(authAdminDO);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr#updateAdminFullName(java.lang.String, java.lang.String)
	 */
	@Override
	public Admin updateAdminFullName(final String id, final String fullName) {
		final	Admin admin = this.getAdmin(id);
		if (admin == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO authAdminDO = new AuthAdminDO();
				authAdminDO.setId(id);
				authAdminDO.setFullName(fullName);
				authAdminDO.setSysUpdateTime(new Date());
				authAdminDOMapper.updateByPrimaryKeySelective(authAdminDO);
			}
		});
		return this.getAdmin(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr#updatePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public Admin updatePassword(final String id, final String password) {
		final	Admin admin = this.getAdmin(id);
		if (admin == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO authAdminDO = new AuthAdminDO();
				authAdminDO.setId(id);
				authAdminDO.setAdminPwd(password);
				authAdminDO.setSysUpdateTime(new Date());
				authAdminDOMapper.updateByPrimaryKeySelective(authAdminDO);
			}
		});
		return this.getAdmin(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr#getAdmin(java.lang.String)
	 */
	@Override
	public Admin getAdmin(String id) {
		AuthAdminDO model = this.authAdminDOMapper.selectByPrimaryKey(id);
		if (model == null) {
			return null;
		}
		return BeanUtil.toAdmin(model);
	}
	
	@Override
	public Admin getAdminByAdminName(String adminName) {
		AuthAdminDO model = this.authAdminDOMapper.selectByAdminName(adminName);
		if (model == null) {
			return null;
		}
		return BeanUtil.toAdmin(model);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr#login(java.lang.String, java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Admin login(final String adminName, final String password, final HttpServletRequest servletRequest) throws AdminException {
		Admin admin = this.getAdminByAdminName(adminName);
		if (admin == null) {
			LOG.error("Admin name not found ,{}", adminName);
			throw AdminException.create(ErrorCode.AdminName_Not_Found);
		}
		final String adminID = admin.getId();
		
		if (!admin.getAdminPwd().equals(password)) {
			LOG.error("Admin name and password not match [{},{}]", adminName,password);
			throw AdminException.create(ErrorCode.AdminName_Password_Not_Match);
		}
		if (!admin.getAdminState().equals(AdminState.Active)) {
			LOG.error("Admin name not active ,{}", adminName);
			throw AdminException.create(ErrorCode.Admin_Hasbeen_Forbit);
		}
		
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO authAdminDO = new AuthAdminDO();
				authAdminDO.setId(adminID);
				authAdminDO.setLastLoginTime(new Date());
				authAdminDOMapper.updateByPrimaryKeySelective(authAdminDO);
			}
		});
		admin = this.getAdmin(adminID);
		servletRequest.getSession().setAttribute(Constants.ADMIN_IN_SESSION_NAME, admin);
		return admin;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr#logout(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void logout(final HttpServletRequest servletRequest) {
		servletRequest.getSession().invalidate();
	}

	@Override
	public Admin getCurrentAdmin(HttpServletRequest servletRequest) {
		return (Admin)servletRequest.getSession().getAttribute(Constants.ADMIN_IN_SESSION_NAME);
	}
	
	@Override
	public Collection<Admin> getAdmins(int offset, int limit) {
		List<AuthAdminDO> authAdminDOs = this.authAdminDOMapper.selectAll(new RowBounds(offset, limit));
		if (authAdminDOs == null || authAdminDOs.size() <=0) {
			return null;
		}
		Collection<Admin> admins = new ArrayList<Admin>();
		for (AuthAdminDO model : authAdminDOs) {
			admins.add(BeanUtil.toAdmin(model));
		}
		return admins;
	}
	
	@Override
	public int getTotalAdmins() {
		return this.authAdminDOMapper.selectTotalAll();
	}
	
	@Override
	public Admin updateAdminState(final String id, final AdminState adminState) {
		final	Admin admin = this.getAdmin(id);
		if (admin == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO authAdminDO = new AuthAdminDO();
				authAdminDO.setId(id);
				authAdminDO.setSysUpdateTime(new Date());
				authAdminDO.setAdminState(adminState.getCode());
				authAdminDOMapper.updateByPrimaryKeySelective(authAdminDO);
			}
		});
		return this.getAdmin(id);
	}

	@Override
	public void deleteAdmin(final String id) {
		Admin admin = this.getAdmin(id);
		if (admin == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				authAdminDOMapper.deleteByPrimaryKey(id);
			}
		});
	}
	
}
