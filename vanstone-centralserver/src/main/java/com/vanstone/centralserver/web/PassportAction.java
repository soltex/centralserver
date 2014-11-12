/**
 * 
 */
package com.vanstone.centralserver.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.centralserver.AbstractWebAction;
import com.vanstone.centralserver.business.sdk.adminservice.AdminException;
import com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr;
import com.vanstone.centralserver.business.sdk.adminservice.AdminException.ErrorCode;
import com.vanstone.webframework.dwz.DWZObject;

/**
 * @author shipeng
 */
@Controller
public class PassportAction extends AbstractWebAction {

	@Autowired
	private IAuthServiceMgr authServiceMgr;
	
	@RequestMapping("/login")
	public String login(@ModelAttribute("passportForm")PassportForm passportForm, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		return "/login";
	}
	
	@RequestMapping("/login-action")
	@ResponseBody
	public DWZObject loginAction(@ModelAttribute("passportForm")PassportForm passportForm, ModelMap modelMap, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			authServiceMgr.login(passportForm.getAdminName(), passportForm.getAdminPwd(), servletRequest);
		} catch (AdminException e) {
			DWZObject object = DWZObject.createErrorObject();
			if (e.getErrorCode().equals(ErrorCode.Admin_Hasbeen_Forbit)) {
				object.setMessage("当前账户已被禁用，请联系管理员开通");
			}else if (e.getErrorCode().equals(ErrorCode.AdminName_Not_Found)) {
				object.setMessage("账户名不存在，请检查");
			}else if (e.getErrorCode().equals(ErrorCode.AdminName_Password_Not_Match)) {
				object.setMessage("账户名密码不匹配，请检查");
			}
			return object;
		}
		DWZObject object = DWZObject.createSuccessObject();
		object.addParam("redirectUrl", "/index.jhtml");
		return object;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		authServiceMgr.logout(servletRequest);
		return "redirect:/login.jhtml";
	}
	
}
