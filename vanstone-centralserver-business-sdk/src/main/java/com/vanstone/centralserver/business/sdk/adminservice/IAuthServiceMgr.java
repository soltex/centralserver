/**
 * 
 */
package com.vanstone.centralserver.business.sdk.adminservice;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.vanstone.business.ObjectDuplicateException;

/**
 * 权限
 * @author shipeng
 */
public interface IAuthServiceMgr {
	
	public static final String SERVICE = "authServiceMgr";
	
	/**
	 * 添加管理员
	 * @param adminName
	 * @param adminPwd
	 * @param fullName
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Admin addAdmin(String adminName, String adminPwd, String fullName) throws ObjectDuplicateException;
	
	/**
	 * 更新管理员姓名
	 * @param id
	 * @param fullName
	 * @return
	 */
	Admin updateAdminFullName(String id, String fullName);
	
	/**
	 * 更新密码
	 * @param id
	 * @param password
	 * @return
	 */
	Admin updatePassword(String id, String password);
	
	/**
	 * 获取管理员信息
	 * @param id
	 * @return
	 */
	Admin getAdmin(String id);
	
	/**
	 * 通过用户名获取Admin
	 * @param adminName
	 * @return
	 */
	Admin getAdminByAdminName(String adminName);
	
	/**
	 * 登陆系统
	 * @param adminName
	 * @param password
	 * @return
	 * @throws AdminException
	 */
	Admin login(String adminName, String password, HttpServletRequest servletRequest) throws AdminException;
	
	/**
	 *  退出系统
	 * @param servletRequest
	 */
	void logout(HttpServletRequest servletRequest);
	
	/**
	 * 获取Admins列表
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<Admin> getAdmins(int offset, int limit);
	
	/**
	 * 获取Admins数量
	 * @return
	 */
	int getTotalAdmins();
	
	/**
	 * 更新AdminState状态
	 * @param id
	 * @param adminState
	 * @return
	 */
	Admin updateAdminState(String id, AdminState adminState);
	
	/**
	 * 删除Admin
	 * @param id
	 */
	void deleteAdmin(String id);
	
}
