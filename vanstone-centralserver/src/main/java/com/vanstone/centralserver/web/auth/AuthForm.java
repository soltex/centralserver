/**
 * 
 */
package com.vanstone.centralserver.web.auth;

import com.vanstone.centralserver.AbstractWebActionForm;

/**
 * @author shipeng
 * 
 */
public class AuthForm extends AbstractWebActionForm {

	private String adminId;
	private String adminName;
	private String adminPwd;
	private String fullName;
	
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
