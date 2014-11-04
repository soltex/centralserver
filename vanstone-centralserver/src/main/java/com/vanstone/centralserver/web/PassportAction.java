/**
 * 
 */
package com.vanstone.centralserver.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanstone.centralserver.AbstractWebAction;

/**
 * @author shipeng
 */
@Controller
public class PassportAction extends AbstractWebAction {

	@RequestMapping("/login")
	public String login(HttpServletRequest serveletRequest, HttpServletResponse servletResponse) {
		return null;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		return null;
	}
	
}
