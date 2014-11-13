/**
 * 
 */
package com.vanstone.centralserver.business.sdk.adminservice;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.ServletUtil;
import com.vanstone.framework.business.ServiceManagerFactory;
import com.vanstone.webframework.dwz.DWZObject;

/**
 * @author shipeng
 */
public class AuthenticationFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		IAuthServiceMgr authServiceMgr = ServiceManagerFactory.getInstance().getService(IAuthServiceMgr.SERVICE);
		Admin admin = authServiceMgr.getCurrentAdmin(servletRequest);
		if (admin == null) {
			//未登录
			if (AuthUtil.isAjax(servletRequest)) {
				//Ajax操作
				if (servletRequest.getRequestURI().indexOf("login") != -1 || servletRequest.getRequestURI().indexOf("logout") != -1) {
					//登录或者登出操作  
					chain.doFilter(servletRequest, servletResponse);
					return;
				}
				//非登录登出页面
				ServletUtil.write(servletResponse, DWZObject.createRedirectObject("/login.jhtml").toJsonString());
				return;
			}else {
				//普通操作
				if (servletRequest.getRequestURI().indexOf("login") != -1 || servletRequest.getRequestURI().indexOf("logout") != -1) {
					//登录或者登出操作  
					chain.doFilter(servletRequest, servletResponse);
					return;
				}
				//非登录登出页面
				servletResponse.sendRedirect("/login.jhtml");
				return;
			}
		}else{
			//已登录
			chain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
		
	}
	
}
