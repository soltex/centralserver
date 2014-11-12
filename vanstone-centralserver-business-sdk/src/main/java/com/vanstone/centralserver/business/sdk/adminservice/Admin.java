/**
 * 
 */
package com.vanstone.centralserver.business.sdk.adminservice;

import java.util.Date;

import com.vanstone.business.def.AbstractBusinessObject;

/**
 * @author shipeng
 * 
 */
public class Admin extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = -1172306604002326812L;
	
	/**ID*/
	private String id;
	/**账号*/
	private String adminName;
	/**密码*/
	private String adminPwd;
	/**写入时间*/
	private Date sysInsertTime;
	/**更新时间*/
	private Date sysUpdateTime;
	/**最近登录时间*/
	private Date lastLoginTime;
	/**管理员状态*/
	private AdminState adminState;
	/**姓名*/
	private String fullName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public Date getSysInsertTime() {
		return sysInsertTime;
	}

	public void setSysInsertTime(Date sysInsertTime) {
		this.sysInsertTime = sysInsertTime;
	}

	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public AdminState getAdminState() {
		return adminState;
	}

	public void setAdminState(AdminState adminState) {
		this.adminState = adminState;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
