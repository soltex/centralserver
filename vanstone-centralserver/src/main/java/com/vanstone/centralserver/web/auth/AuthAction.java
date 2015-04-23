/**
 * 
 */
package com.vanstone.centralserver.web.auth;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.AbstractWebAction;
import com.vanstone.centralserver.business.sdk.adminservice.Admin;
import com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.common.util.random.RandomNumber;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.common.util.web.PageUtil;
import com.vanstone.webframework.dwz.DialogViewCommandObject;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;

/**
 * @author shipeng
 */
@Controller
@RequestMapping("/admin/auth")
public class AuthAction extends AbstractWebAction {
	
	@Autowired
	private IAuthServiceMgr authServiceMgr;
	
	@RequestMapping("/view-admins")
	public String viewAdmins(@RequestParam(value="p",required=false)Integer p, ModelMap modelMap) {
		if (p == null) {
			p = 1;
		}
		int total = this.authServiceMgr.getTotalAdmins();
		PageUtil<Admin> pageUtil = new PageUtil<Admin>(total, p, Constants.DEFAULT_ADMIN_PAGESIZE);
		PageInfo<Admin> pageInfo = pageUtil.getPageInfo();
		Collection<Admin> admins = this.authServiceMgr.getAdmins(pageUtil.getOffset(), pageUtil.getSize());
		pageInfo.addObjects(admins);
		modelMap.put("pageInfo", pageInfo);
		return "/auth/view-admins";
	}
	
	@RequestMapping("/add-admin")
	public String addAdmin(@ModelAttribute("authForm")AuthForm authForm, ModelMap modelMap) {
		return "/auth/add-admin";
	}
	
	@RequestMapping("/add-admin-action")
	@ResponseBody
	public DialogViewCommandObject addAdminAction(@ModelAttribute("authForm")AuthForm authForm, ModelMap modelMap) {
		try {
			this.authServiceMgr.addAdmin(authForm.getAdminName(), authForm.getAdminPwd(), authForm.getFullName());
		} catch (ObjectDuplicateException e) {
			DialogViewCommandObject object = ViewCommandHelper.createErrorDialog(false, true);
			object.setMessage("账户名" + authForm.getAdminName() + "重复，请重新选择。");
			return object;
		}
		DialogViewCommandObject successObject = ViewCommandHelper.createSuccessDialog(true, false);
		successObject.setForwardUrl("/admin/auth/view-admins.jhtml");
		successObject.setRel(authForm.getRel());
		return successObject;
	}
	
	@RequestMapping("/update-fullname/{id}")
	public String updateFullName(@PathVariable("id")String id, @ModelAttribute("authForm")AuthForm authForm, ModelMap modelMap) {
		Admin admin = this.authServiceMgr.getAdmin(id);
		if (admin == null) {
			throw new IllegalArgumentException();
		}
		authForm.setFullName(admin.getFullName());
		authForm.setAdminId(id);
		return "/auth/update-fullname";
	}
	
	@RequestMapping("/update-fullname-action/{id}")
	@ResponseBody
	public DialogViewCommandObject updateFullNameAction(@PathVariable("id")String id, @ModelAttribute("authForm")AuthForm authForm, ModelMap modelMap) {
		this.authServiceMgr.updateAdminFullName(id, authForm.getFullName());
		DialogViewCommandObject successObject = ViewCommandHelper.createSuccessDialog(true, false);
		successObject.setMessage("修改姓名成功");
		successObject.setForwardUrl("/admin/auth/view-admins.jhtml");
		successObject.setRel(authForm.getRel());
		return successObject;
	}
	
	@RequestMapping("/reset-password/{id}")
	@ResponseBody
	public ViewCommandObject resetPassword(@PathVariable("id")String id) {
		String newPassword = RandomNumber.randomNumber(6);
		this.authServiceMgr.updatePassword(id, newPassword);
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("密码已重置.");
		
		object.setForwardUrl("/admin/auth/view-admins.jhtml");
		return object;
	}
	
	@RequestMapping("/delete-admin/{id}")
	@ResponseBody
	public ViewCommandObject deleteAdmin(@PathVariable("id")String id) {
		this.authServiceMgr.deleteAdmin(id);
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("管理员已删除");
		object.setForwardUrl("/auth/admin/view-admins.jhtml");
		return object;
	}
	
}
