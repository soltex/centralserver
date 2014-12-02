/**
 * 
 */
package com.vanstone.centralserver.web.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.centralserver.AbstractWebAction;
import com.vanstone.centralserver.business.sdk.weixin.AppnameExistsException;
import com.vanstone.centralserver.business.sdk.weixin.FlushResult;
import com.vanstone.centralserver.business.sdk.weixin.IWeixinInfo;
import com.vanstone.centralserver.business.sdk.weixin.IWeixinServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.weixin.sdk.WeixinInfoBuilder;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;

/**
 * @author shipeng
 */
@Controller
@RequestMapping("/admin/weixin")
public class WeixinInfoAction extends AbstractWebAction{
	
	@Autowired
	private IWeixinServiceMgr weixinServiceMgr;
	
	@RequestMapping("/add-weixininfo")
	public String addWeixinInfo(HttpServletRequest servletRequest,HttpServletResponse servletResponse,@ModelAttribute("weixinInfoForm")WeixinInfoForm form) {
		return "/weixin/add-weixininfo";
	}
	
	@RequestMapping(value="/add-weixininfo-action")
	@ResponseBody
	public ViewCommandObject addWeixinInfoAction(HttpServletRequest servletRequest, HttpServletResponse servletResponse,@ModelAttribute("weixinInfoForm")WeixinInfoForm form) {
		IWeixinInfo weixinInfo = WeixinInfoBuilder.create(form.getId(), form.getAppid(), form.getAppSecret(), form.getContent());
		try {
			this.weixinServiceMgr.addWeixinInfo(weixinInfo);
		} catch (WeixinException e) {
			e.printStackTrace();
			return ViewCommandHelper.createErrorObject(e.getErrorCode().getDesc());
		} catch (AppnameExistsException e) {
			e.printStackTrace();
			return ViewCommandHelper.createErrorObject("Appname : " + form.getId() + " 已存在，请检查");
		}
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("添加成功");
		object.setForwardUrl("/admin/weixin/view-weixininfos.jhtml");
		return object;
	}
	
	@RequestMapping("/view-weixininfos")
	public String viewWeixinInfos(@RequestParam(value="key",required=false)String key, @RequestParam(value="p",required=false)Integer p, ModelMap modelMap) {
		PageInfo<IWeixinInfo> pageInfo = this.weixinServiceMgr.getWeixinInfos(key, p != null ? p : 1, Constants.DEFAULT_ADMIN_PAGESIZE);
		modelMap.put("pageInfo", pageInfo);
		return "/weixin/view-weixininfos";
	}
	
	@RequestMapping("/update-all-weixins")
	@ResponseBody
	public ViewCommandObject updateAllWeixins() {
		FlushResult flushResult = this.weixinServiceMgr.flushAllAccessToken();
		if (flushResult.isSuccess()) {
			ViewCommandObject object = ViewCommandHelper.createSuccessObject("刷新成功");
			object.setForwardUrl("/admin/weixin/view-weixininfos.jhtml");
			return object;
		}else{
			ViewCommandObject object = ViewCommandHelper.createErrorObject("刷新失败 \n " +  flushResult.toResultString());
			object.setForwardUrl("/admin/weixin/view-weixininfos.jhtml");
			return object;
		}
	}
}
