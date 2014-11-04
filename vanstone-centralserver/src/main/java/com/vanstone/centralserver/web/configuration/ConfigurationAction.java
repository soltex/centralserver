/**
 * 
 */
package com.vanstone.centralserver.web.configuration;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.AbstractWebAction;
import com.vanstone.centralserver.business.sdk.configuration.IConfInfo;
import com.vanstone.centralserver.business.sdk.configuration.IConfigurationServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.common.util.web.PageInfo;

/**
 * 配置管理Action
 * @author shipeng
 */
@Controller
@RequestMapping("/admin/configuration")
public class ConfigurationAction extends AbstractWebAction {

	@Autowired
	private IConfigurationServiceMgr configurationServiceMgr;
	
	@RequestMapping("/view-configurations")
	public String viewConfigurations(@RequestParam(value="key",required=false)String key, @RequestParam(value="p",required=false) Integer p, ModelMap modelMap, @ModelAttribute("confForm")ConfForm confForm) {
		if (p == null) {
			p = 1;
		}
		PageInfo<IConfInfo> pageInfo = this.configurationServiceMgr.getConfInfosPageInfo(key, p, Constants.DEFAULT_ADMIN_PAGESIZE);
		modelMap.put("pageInfo", pageInfo);
		return "/configuration/view-configurations";
	}
	
	@RequestMapping("/add-configuration")
	public String addConfiguration(@ModelAttribute("confForm")ConfForm confForm) {
		return "/configuration/add-configuration";
	}
	
	@RequestMapping("/add-configuration-action")
	public String addConfigurationAction(@ModelAttribute("confForm")ConfForm confForm) {
		try {
			this.configurationServiceMgr.addConf(confForm.getGroupId(), confForm.getDataId(), confForm.getValue());
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/configuration/view-configurations.jhtml";
	}
	
	@RequestMapping("/clear-all-configurations")
	public String clearAllConfigurations() {
		Collection<String> groupIds = this.configurationServiceMgr.getGroups();
		if (!CollectionUtils.isEmpty(groupIds)) {
			for (String groupId : groupIds) {
				this.configurationServiceMgr.deleteConfsByGroupId(groupId);
			}
		}
		return "redirect:/admin/configuration/view-configurations.html";
	}
}
