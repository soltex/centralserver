/**
 * 
 */
package com.vanstone.centralserver.web;

import com.vanstone.centralserver.AbstractWebActionForm;

/**
 * @author shipeng
 * 
 */
public class PassportForm extends AbstractWebActionForm {

	private String adminName;
	private String adminPwd;

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

}
