/**
 * 
 */
package com.vanstone.centralserver.adminservice.sdk.services.impl;

import com.vanstone.business.lang.EnumUtils;
import com.vanstone.centralserver.adminservice.sdk.persistence.object.AuthAdminDO;
import com.vanstone.centralserver.business.sdk.adminservice.Admin;
import com.vanstone.centralserver.business.sdk.adminservice.AdminState;

/**
 * @author shipeng
 */
public class BeanUtil {

	public static Admin toAdmin(AuthAdminDO authAdminDO) {
		Admin admin = new Admin();
		admin.setId(authAdminDO.getId());
		admin.setAdminName(authAdminDO.getAdminName());
		admin.setAdminPwd(authAdminDO.getAdminPwd());
		admin.setSysInsertTime(authAdminDO.getSysInsertTime());
		admin.setSysUpdateTime(authAdminDO.getSysUpdateTime());
		admin.setLastLoginTime(authAdminDO.getLastLoginTime());
		admin.setAdminState(authAdminDO.getAdminState() != null ? (AdminState) EnumUtils.getByCode(authAdminDO.getAdminState(), AdminState.class) : null);
		admin.setFullName(authAdminDO.getFullName());
		return admin;
	}

	public static AuthAdminDO toAuthAdminDO(Admin admin) {
		AuthAdminDO authAdminDO = new AuthAdminDO();
		authAdminDO.setId(admin.getId());
		authAdminDO.setAdminName(admin.getAdminName());
		authAdminDO.setAdminPwd(admin.getAdminPwd());
		authAdminDO.setSysInsertTime(admin.getSysInsertTime());
		authAdminDO.setSysUpdateTime(admin.getSysUpdateTime());
		authAdminDO.setLastLoginTime(admin.getLastLoginTime());
		authAdminDO.setAdminState(admin.getAdminState() != null ? admin.getAdminState().getCode() : null);
		authAdminDO.setFullName(admin.getFullName());
		return authAdminDO;
	}

}
