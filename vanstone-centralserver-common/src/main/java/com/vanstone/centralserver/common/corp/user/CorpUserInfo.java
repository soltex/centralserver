/**
 * 
 */
package com.vanstone.centralserver.common.corp.user;

import java.util.ArrayList;
import java.util.List;

import com.vanstone.centralserver.common.corp.UserStatus;
import com.vanstone.centralserver.common.weixin.wrap.Sex;

/**
 * @author shipeng 企业用户详情
 */
public class CorpUserInfo {
	private String userid;
	private String name;
	private List<Integer> departmentIDs = new ArrayList<Integer>();
	private String position;
	private String mobile;
	private Sex sex;
	private String email;
	private String weixinid;
	private String avatar;
	private UserStatus userStatus;
	private List<UserExtAttr> attrs = new ArrayList<UserExtAttr>();

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeixinid() {
		return weixinid;
	}

	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public List<Integer> getDepartmentIDs() {
		return departmentIDs;
	}

	public List<UserExtAttr> getAttrs() {
		return attrs;
	}
	
	public void addDepartmentID(int departmentID) {
		if (!this.departmentIDs.contains(departmentID)) {
			this.departmentIDs.add(departmentID);
		}
	}
	
	public void addDepartmentIDs(List<Integer> departmentIDs) {
		this.departmentIDs.addAll(departmentIDs);
	}
	
	public void addUserExtAttr(String name, String value) {
		UserExtAttr extAttr = new UserExtAttr();
		extAttr.setName(name);
		extAttr.setValue(value);
		this.attrs.add(extAttr);
	}
	
	public void clearDepartmentIDs() {
		this.departmentIDs.clear();
	}
	
	public void clearAttrs() {
		this.attrs.clear();
	}
	
}
