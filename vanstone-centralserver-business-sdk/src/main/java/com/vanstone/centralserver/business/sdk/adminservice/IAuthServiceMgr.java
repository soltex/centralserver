/**
 * 
 */
package com.vanstone.centralserver.business.sdk.adminservice;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

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
	Admin addAdmin(@NotBlank String adminName, @NotBlank String adminPwd, String fullName) throws ObjectDuplicateException;
	
	/**
	 * 更新管理员姓名
	 * @param id
	 * @param fullName
	 * @return
	 */
	Admin updateAdminFullName(@NotBlank String id, String fullName);
	
	/**
	 * 更新密码
	 * @param id
	 * @param password
	 * @return
	 */
	Admin updatePassword(@NotBlank String id, @NotBlank String password);
	
	/**
	 * 获取管理员信息
	 * @param id
	 * @return
	 */
	Admin getAdmin(@NotBlank String id);
	
	/**
	 * 通过用户名获取Admin
	 * @param adminName
	 * @return
	 */
	Admin getAdminByAdminName(@NotBlank String adminName);
	
	/**
	 * 登陆系统
	 * @param adminName
	 * @param password
	 * @return
	 * @throws AdminException
	 */
	Admin login(@NotBlank String adminName, @NotBlank String password, @NotNull HttpServletRequest servletRequest) throws AdminException;
	
	/**
	 *  退出系统
	 * @param servletRequest
	 */
	void logout(@NotNull HttpServletRequest servletRequest);
	
	/**
	 * 获取当前Admin
	 * @param servletRequest
	 * @return
	 */
	Admin getCurrentAdmin(@NotNull HttpServletRequest servletRequest);
	
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
	Admin updateAdminState(@NotBlank String id, @NotBlank AdminState adminState);
	
	/**
	 * 删除Admin
	 * @param id
	 */
	void deleteAdmin(@NotBlank String id);
	
}
