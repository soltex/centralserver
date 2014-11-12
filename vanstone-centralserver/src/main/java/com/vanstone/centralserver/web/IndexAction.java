/**
 * 
 */
package com.vanstone.centralserver.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanstone.centralserver.AbstractWebAction;

/**
 * @author shipeng
 */
@Controller("indexAction")
public class IndexAction extends AbstractWebAction {
	
	@RequestMapping("/index")
	public String index(ModelMap modelMap) {
		return "/index";
	}
	
	@RequestMapping("/index_sub")
	public String indexSub(ModelMap modelMap) {
		return "/index_sub";
	}
	
}
